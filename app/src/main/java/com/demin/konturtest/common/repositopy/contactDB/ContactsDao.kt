package com.demin.konturtest.common.repositopy.contactDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demin.konturtest.common.entity.Contact
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ContactsDao {

    @Query("SELECT * FROM Contact")
    fun getContacts(): Single<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contacts: List<Contact>): Completable

    @Query("SELECT * FROM Contact WHERE name LIKE '%' || :value || '%' " +
            "OR phone LIKE '%' || :value || '%'")
    fun findContactByNameAndPhoneNumber(value: String): Single<List<Contact>>

}