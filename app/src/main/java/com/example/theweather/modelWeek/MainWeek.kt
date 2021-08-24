package com.example.theweather.modelWeek

import com.google.gson.annotations.SerializedName

data class MainWeek(
    @SerializedName("temp") var temp : Double,
    @SerializedName("feels_like") var feelsLike : Double,
    @SerializedName("temp_min") var tempMin : Double,
    @SerializedName("temp_max") var tempMax : Double,
    @SerializedName("pressure") var pressure : Double,
    @SerializedName("sea_level") var seaLevel : Double,
    @SerializedName("grnd_level") var grndLevel : Double,
    @SerializedName("humidity") var humidity : Double,
    @SerializedName("temp_kf") var tempKf : Double

)
