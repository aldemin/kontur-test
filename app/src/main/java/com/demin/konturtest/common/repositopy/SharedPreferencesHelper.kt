package com.demin.konturtest.common.repositopy

import android.content.Context
import java.util.*
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var lastLoadContactListWithStart: Date = Date()
        set(value) {
            field = value
            sharedPreferences.edit()
                .putLong(LAST_START_DATE, value.time)
                .apply()
        }
        get() = Date(sharedPreferences.getLong(LAST_START_DATE, 0))

    companion object {

        private const val PREF_NAME = "application preferences"
        private const val LAST_START_DATE = "last load date"

    }
}