package com.demin.konturtest.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MainFragmentViewModel(app: Application) : AndroidViewModel(app) {

    class Factory @Inject constructor(private val app: Application) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == MainFragmentViewModel::class)
            return MainFragmentViewModel(app) as T
        }
    }

}