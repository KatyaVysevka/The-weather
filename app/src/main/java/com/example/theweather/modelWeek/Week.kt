package com.example.theweather.modelWeek

import com.google.gson.annotations.SerializedName


data class Week (

    @SerializedName("lat") var lat : Double,
    @SerializedName("lon") var lon : Double,
    @SerializedName("timezone") var timezone : String,
    @SerializedName("timezone_offset") var timezoneOffset : Int,
    @SerializedName("daily") var daily : List<Daily>

)