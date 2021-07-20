package com.demin.konturtest.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.demin.konturtest.common.api.ContactListApi
import com.demin.konturtest.common.entity.Contact
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val mApp: Application,
    private val mContactListApi: ContactListApi
) : AndroidViewModel(mApp) {

    private val mFirstSource = "https://raw.githubusercontent.com/SkbkonturMobile/" +
            "mobile-test-droid/master/json/generated-01.json"
    private val mSecondSource = "https://raw.githubusercontent.com/SkbkonturMobile/" +
            "mobile-test-droid/master/json/generated-02.json"
    private val mThirdSource = "https://raw.githubusercontent.com/SkbkonturMobile/" +
            "mobile-test-droid/master/json/generated-03.json"

    fun loadContacts(observer: Observer<List<Contact>>) {
        Observable.zip(
            mContactListApi.getContactList(mFirstSource),
            mContactListApi.getContactList(mSecondSource),
            mContactListApi.getContactList(mThirdSource),
            Function3<List<Contact>, List<Contact>, List<Contact>, List<Contact>> {
                    firstList, secondList, thirdList ->
                return@Function3 firstList + secondList + thirdList
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                saveToCache(it)
            }
            .subscribe(observer)

    }

    private fun saveToCache(contactList: List<Contact>) {
        // TODO
        Log.d(TAG, "saveToCache: ${contactList.size}")
    }

    class Factory @Inject constructor(
        private val mApp: Application,
        private val mContactListApi: ContactListApi,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == MainFragmentViewModel::class.java)
            return MainFragmentViewModel(mApp, mContactListApi) as T
        }
    }

    companion object {

        private val TAG = MainFragmentViewModel::class.simpleName

    }

}