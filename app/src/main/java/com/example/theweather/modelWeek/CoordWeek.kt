package com.example.theweather.modelWeek

import com.google.gson.annotations.SerializedName

data class CoordWeek(
    @SerializedName("lat") var lat : Double,
    @SerializedName("lon") var lon : Double
)
