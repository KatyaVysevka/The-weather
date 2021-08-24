package com.example.theweather.modelWeek

import com.google.gson.annotations.SerializedName

data class ListWeek(
    @SerializedName("dt") var dt : Double,
    @SerializedName("main") var main : MainWeek,
    @SerializedName("weather") var weather : List<WeatherWeek>,
    @SerializedName("clouds") var clouds : CloudsWeek,
    @SerializedName("wind") var wind : WindWeek,
    @SerializedName("visibility") var visibility : Double,
    @SerializedName("pop") var pop : Double,
    @SerializedName("sys") var sys : SysWeek,
    @SerializedName("dt_txt") var dtTxt : String //"2021-08-21 15:00:00"
)
