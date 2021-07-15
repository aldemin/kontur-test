package com.demin.konturtest.common

import android.app.Application
import com.demin.konturtest.ui.DetailsFragment
import com.demin.konturtest.ui.MainActivity
import com.demin.konturtest.ui.MainFragment
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
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