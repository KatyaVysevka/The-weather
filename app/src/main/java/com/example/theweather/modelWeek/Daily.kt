package com.example.theweather.modelWeek

import com.google.gson.annotations.SerializedName


data class Daily (

    @SerializedName("dt") var dt : Double,
    @SerializedName("sunrise") var sunrise : Double,
    @SerializedName("sunset") var sunset : Double,
    @SerializedName("moonrise") var moonrise : Double,
    @SerializedName("moonset") var moonset : Double,
    @SerializedName("moon_phase") var moonPhase : Double,
    @SerializedName("temp") var temp : Temp,
    @SerializedName("feels_like") var feelsLike : FeelsLike,
    @SerializedName("pressure") var pressure : Double,
    @SerializedName("humidity") var humidity : Double,
    @SerializedName("dew_point") var dewPoint : Double,
    @SerializedName("wind_speed") var windSpeed : Double,
    @SerializedName("wind_deg") var windDeg : Double,
    @SerializedName("wind_gust") var windGust : Double,
    @SerializedName("weather") var weather : List<Weather>,
    @SerializedName("clouds") var clouds : Double,
    @SerializedName("pop") var pop : Double,
    @SerializedName("uvi") var uvi : Double

)