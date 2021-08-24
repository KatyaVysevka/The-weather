package com.example.theweather.rest

import com.example.theweather.model.WeatherResponse
import com.example.theweather.modelWeek.Week
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//http://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=815876ddaac34f06620b42ff5366f3d0&units=metric
interface WeatherAPI {
    @GET("weather?")
    fun getWeatherRetrofit(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("units") units: String,
    ): Call<WeatherResponse>


    @GET("weather?")
    fun getWeatherRetrofitRx(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
    ): Single<WeatherResponse>

    //http://api.openweathermap.org/data/2.5/onecall?lat=30.4219983&lon=-122.084&appid=815876ddaac34f06620b42ff5366f3d0&exclude=hourly,minutely,alerts,current
    //https://api.openweathermap.org/data/2.5/forecast?q=Minsk&appid=815876ddaac34f06620b42ff5366f3d0
    @GET("forecast?")
    fun getWeatherDailyRetrofit(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("units") units: String,

    ): Call<Week>
}