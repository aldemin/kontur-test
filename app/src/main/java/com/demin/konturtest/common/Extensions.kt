package com.demin.konturtest.common

import android.content.Context
import com.demin.konturtest.App
import com.demin.konturtest.common.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }