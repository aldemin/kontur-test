package com.demin.konturtest

import android.app.Application
import com.demin.konturtest.common.di.AppComponent
import com.demin.konturtest.common.di.AppModule
import com.demin.konturtest.common.di.DaggerAppComponent
import com.demin.konturtest.common.di.SourceUrlModule

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .sourceUrlModule(
                SourceUrlModule(
                    baseURL, arrayListOf(
                        firstSourceUrl, secondSourceUrl, thirdSourceUrl
                    )
                )
            )
            .build()
    }

    companion object {

        private const val baseURL = "https://raw.githubusercontent.com/" +
                "SkbkonturMobile/mobile-test-droid/master/json/"
        private const val firstSourceUrl = "generated-01.json"
        private const val secondSourceUrl = "generated-02.json"
        private const val thirdSourceUrl = "generated-03.json"

    }

}