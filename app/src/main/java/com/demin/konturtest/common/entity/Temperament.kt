package com.demin.konturtest.common.entity

import com.google.gson.annotations.SerializedName

enum class Temperament(val value: String) {
    @SerializedName("melancholic")
    MELANCHOLIC("melancholic"),

    @SerializedName("phlegmatic")
    PHLEGMATIC("phlegmatic"),

    @SerializedName("sanguine")
    SANGUINE("sanguine"),

    @SerializedName("choleric")
    CHOLERIC("choleric")
}