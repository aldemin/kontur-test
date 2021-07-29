package com.demin.konturtest.ui.viewmodel

import com.demin.konturtest.common.entity.Contact

data class MainFragmentViewState(
    var isLoading: Boolean = false,
    var isErrorShow: Boolean = false,
    var errorMessage: String = "",
    var contactList: List<Contact> = arrayListOf(),
    var isRefreshingViewShowing: Boolean = false
)