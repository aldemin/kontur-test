package com.demin.konturtest.common

import android.content.Context
import com.demin.konturtest.App

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }