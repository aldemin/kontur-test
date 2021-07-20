package com.demin.konturtest.common.di

import android.app.Application
import com.demin.konturtest.common.api.ContactListApi
import com.demin.konturtest.ui.DetailsFragment
import com.demin.konturtest.ui.MainActivity
import com.demin.konturtest.ui.MainFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Component(modules = [AppModule::class, RetrofitModule::class, SourceUrlModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: DetailsFragment)
}

@Module
class AppModule(private val app: Application) {
    @Provides
    fun provideApplication() = app
}

@Module
class RetrofitModule {

    @Provides
    fun provideRetrofitApi() = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
        )
        // TODO: 19.07.2021
        .baseUrl("https://raw.githubusercontent.com/SkbkonturMobile/" +
                "mobile-test-droid/master/json/generated-01.json/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ContactListApi::class.java)
}

@Module
class SourceUrlModule {

    // TODO: workaround
    private val firstSourceUrl = "https://raw.githubusercontent.com/SkbkonturMobile/" +
            "mobile-test-droid/master/json/generated-01.json"

    private val secondSourceUrl = "https://raw.githubusercontent.com/SkbkonturMobile/" +
            "mobile-test-droid/master/json/generated-02.json"

    private val thirdSourceUrl = "https://raw.githubusercontent.com/SkbkonturMobile/" +
            "mobile-test-droid/master/json/generated-03.json"

    @FirstSource
    fun provideFirstSourceUrl() = firstSourceUrl

    @SecondSource
    fun provideSecondSourceUrl() = secondSourceUrl

    @ThirdSource
    fun provideThirdSourceUrl() = thirdSourceUrl

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FirstSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SecondSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ThirdSource
// TODO: end of todo