package com.demin.konturtest.common.api

import com.demin.konturtest.common.entity.Contact
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface ContactListApi {

    @GET
    fun getContactList(@Url url: String): Observable<List<Contact>>

}