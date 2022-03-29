package com.example.covidmodule.response

import com.google.gson.annotations.SerializedName

data class SummaryViewData(
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("deaths")
    val deaths: Int
)