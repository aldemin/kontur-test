package com.demin.konturtest

import android.app.Application
import com.demin.konturtest.common.di.AppComponent
import com.demin.konturtest.common.di.AppModule
import com.demin.konturtest.common.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}