package com.demin.konturtest.common.repositopy.contactDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demin.konturtest.common.entity.Contact
import com.demin.konturtest.common.entity.EducationPeriod

@Database(
    entities = arrayOf(Contact::class, EducationPeriod::class),
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class ContactsDB : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
}