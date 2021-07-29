package com.demin.konturtest.common.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: String,
    val educationPeriod: EducationPeriod
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable<EducationPeriod>(ClassLoader.getSystemClassLoader())!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.apply {
            writeString(id)
            writeString(name)
            writeString(phone)
            writeFloat(height)
            writeString(biography)
            writeString(temperament)
            writeParcelable(educationPeriod, 0)
        }
    }

    companion object {

        @JvmField
        final val CREATOR: Parcelable.Creator<Contact> = object : Parcelable.Creator<Contact> {
            override fun createFromParcel(source: Parcel): Contact {
                return Contact(source)
            }

            override fun newArray(size: Int): Array<Contact?> {
                return arrayOfNulls(size)
            }
        }
    }

}