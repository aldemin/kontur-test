package com.demin.konturtest.common.entity

import android.os.Parcel
import android.os.Parcelable

data class EducationPeriod(
    val start: String,
    val end: String
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString()!!, parcel.readString()!!)

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel?, flag: Int) {
        parcel?.apply {
            writeString(start)
            writeString(end)
        }
    }

    companion object {
        @JvmField
        final val CREATOR: Parcelable.Creator<EducationPeriod> =
            object : Parcelable.Creator<EducationPeriod> {
                override fun createFromParcel(source: Parcel): EducationPeriod {
                    return EducationPeriod(source)
                }

                override fun newArray(size: Int): Array<EducationPeriod?> {
                    return arrayOfNulls(size)
                }
            }
    }

}