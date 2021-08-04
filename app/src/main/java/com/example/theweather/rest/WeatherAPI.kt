package com.example.theweather.rest

import com.example.theweather.model.WeatherResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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


}