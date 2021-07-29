package com.demin.konturtest.ui.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.demin.konturtest.common.api.ApiSourceData
import com.demin.konturtest.common.api.ContactListApi
import com.demin.konturtest.common.entity.Contact
import com.demin.konturtest.common.repositopy.SharedPreferencesHelper
import com.demin.konturtest.common.repositopy.contactDB.ContactsDB
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val mContactListApi: ContactListApi,
    private val mSourceData: ApiSourceData,
    private val mContactsDB: ContactsDB,
    private val mSharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val mViewState = MutableLiveData(MainFragmentViewState())

    private var mLoadContactsDisposable: Disposable? = null
    private val mLoadContactsObserver = object : CompletableObserver {

        override fun onSubscribe(d: Disposable) {
            mLoadContactsDisposable = d
            mViewState.value =
                currentViewState().copy(isLoading = true)
        }

        override fun onError(e: Throwable) {
            showError(e)
        }

        override fun onComplete() {
            updateListFromDB()
        }

    }
    private val mRefreshContactsObserver = object : CompletableObserver {

        override fun onSubscribe(d: Disposable) {
            mLoadContactsDisposable = d
        }

        override fun onError(e: Throwable) {
            showError(e)
        }

        override fun onComplete() {
            updateListFromDB()
        }

    }

    private var mUpdateListFromDbDisposable: Disposable? = null
    private val mUpdateListFromDbObserver = object : SingleObserver<List<Contact>> {

        override fun onSubscribe(d: Disposable) {
            mUpdateListFromDbDisposable = d
        }

        override fun onSuccess(t: List<Contact>) {
            mViewState.value = currentViewState().copy(
                isLoading = false,
                contactList = t,
                isRefreshingViewShowing = false
            )
        }

        override fun onError(e: Throwable) {
            showError(e)
        }
    }

    private var mSearchContactDisposable: Disposable? = null
    private val mSearchContactObserver = object : SingleObserver<List<Contact>> {

        override fun onSubscribe(d: Disposable) {
            mSearchContactDisposable = d
            mViewState.value = currentViewState().copy(isLoading = true)
        }

        override fun onSuccess(t: List<Contact>) {
            Log.d("MainFragment", "onSuccess: $t")
            mViewState.value = currentViewState().copy(isLoading = false, contactList = t)
        }

        override fun onError(e: Throwable) {
            showError(e)
        }
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        mLoadContactsDisposable?.dispose()
        mSearchContactDisposable?.dispose()
        mUpdateListFromDbDisposable?.dispose()
        super.onCleared()
    }

    fun onCreateFragment(savedInstantState: Bundle?) {
        Log.d(TAG, "onCreateFragment")
        if (savedInstantState == null) {
            val lastUpdateDelta =
                Date().time - mSharedPreferencesHelper.lastLoadContactListWithStart.time
            Log.d(TAG, "onCreateFragment: $lastUpdateDelta")
            if (lastUpdateDelta >= ONE_SECOND_IN_MILLIS) {
                loadAndSaveContactList(mLoadContactsObserver)
                mSharedPreferencesHelper.lastLoadContactListWithStart = Date()
            } else {
                updateListFromDB()
            }

        }
    }

    fun searchContact(value: String) {
        Log.d(TAG, "searchContact: $value")
        mContactsDB.contactsDao().findContactByNameAndPhoneNumber(value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(mSearchContactObserver)
    }

    fun updateContactList() {
        Log.d(TAG, "updateContactList")
        loadAndSaveContactList(mRefreshContactsObserver)
    }

    fun viewState(): LiveData<MainFragmentViewState> = mViewState

    private fun currentViewState() = mViewState.value!!

    private fun loadAndSaveContactList(observer: CompletableObserver) {
        Log.d(TAG, "loadAndSaveContactList")
        Observable.zip(
            mContactListApi.getContactList(mSourceData.sources[0]),
            mContactListApi.getContactList(mSourceData.sources[1]),
            mContactListApi.getContactList(mSourceData.sources[2]),
            Function3<List<Contact>, List<Contact>, List<Contact>, List<Contact>> {
                    firstList, secondList, thirdList ->
                return@Function3 firstList + secondList + thirdList
            })
            .flatMapCompletable {
                mContactsDB.contactsDao().insertContacts(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun updateListFromDB() {
        Log.d(TAG, "updateListFromDB")
        mContactsDB.contactsDao().getContacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(mUpdateListFromDbObserver)
    }

    private fun showError(e: Throwable) {
        Log.d(TAG, "showError: $e")
        // imitation of single live event
        mViewState.value = currentViewState().copy(
            isLoading = false,
            isErrorShow = true,
            errorMessage = e.message ?: ""
        )
        mViewState.value = currentViewState().copy(
            isLoading = false,
            isErrorShow = false,
            errorMessage = ""
        )
    }

    class Factory @Inject constructor(
        private val mContactListApi: ContactListApi,
        private val mSourceData: ApiSourceData,
        private val mContactsDB: ContactsDB,
        private val mLastStartDate: SharedPreferencesHelper
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == MainFragmentViewModel::class.java)
            return MainFragmentViewModel(
                mContactListApi,
                mSourceData,
                mContactsDB,
                mLastStartDate
            ) as T
        }
    }

    companion object {

        private val TAG = MainFragmentViewModel::class.simpleName
        private const val ONE_SECOND_IN_MILLIS = 60000L

    }

}