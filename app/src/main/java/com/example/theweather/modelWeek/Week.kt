package com.example.theweather.modelWeek

import com.google.gson.annotations.SerializedName


data class Week (
    @SerializedName("cod") var cod : String,
    @SerializedName("message") var message : Double,
    @SerializedName("cnt") var cnt : Double,
    @SerializedName("list") var list : List<ListWeek>,
    @SerializedName("city") var city : City

)