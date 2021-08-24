package com.example.theweather.modelWeek

import com.google.gson.annotations.SerializedName

data class WindWeek(
    @SerializedName("speed") var speed : Double,
    @SerializedName("deg") var deg : Double,
    @SerializedName("gust") var gust : Double
)
