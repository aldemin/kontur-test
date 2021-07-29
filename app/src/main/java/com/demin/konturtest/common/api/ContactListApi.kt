package com.demin.konturtest.common.api

import com.demin.konturtest.common.entity.Contact
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactListApi {

    @GET("{fileName}")
    fun getContactList(@Path("fileName") fileName: String): Observable<List<Contact>>

}