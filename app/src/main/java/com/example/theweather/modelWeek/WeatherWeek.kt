package com.example.theweather.modelWeek

import com.google.gson.annotations.SerializedName


data class WeatherWeek (

    @SerializedName("id") var id : Double,
    @SerializedName("main") var main : String,
    @SerializedName("description") var description : String,
    @SerializedName("icon") var icon : String

)