package com.demin.konturtest.common.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.demin.konturtest.common.api.ApiSourceData
import com.demin.konturtest.common.api.ContactListApi
import com.demin.konturtest.common.repositopy.contactDB.ContactsDB
import com.demin.konturtest.ui.MainActivity
import com.demin.konturtest.ui.MainFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Component(
    modules = [
        AppModule::class, RetrofitModule::class,
        SourceUrlModule::class, ContactDataBaseModule::class]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
}

@Module(includes = [AppBindModule::class])
class AppModule(private val app: Application) {

    @Provides
    fun provideApplication() = app

}

@Module
interface AppBindModule {

    @Binds
    fun bindContext(app: Application): Context

}

@Module
class RetrofitModule {

    @Provides
    fun provideRetrofitApi(sourceData: ApiSourceData) = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
        )
        .baseUrl(sourceData.baseURL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ContactListApi::class.java)
}

@Module
class SourceUrlModule(private val mBaseURL: String, private val mSources: List<String>) {

    @Provides
    fun provideContactListSourceData() = ApiSourceData(mBaseURL, mSources)

}

@Module
class ContactDataBaseModule() {

    @Provides
    fun provideContactDataBase(app: Application) =
        Room.databaseBuilder(app, ContactsDB::class.java, "Contact").build()

}