package com.demin.konturtest.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class DetailsFragmentViewModel(app: Application) : AndroidViewModel(app) {

    class Factory @Inject constructor(private val app: Application) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == MainFragmentViewModel::class.java)
            return DetailsFragmentViewModel(app) as T
        }
    }

}