package com.example.theweather.rest

import com.example.theweather.model.WeatherResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI {
    @GET("weather?")
    fun getWeatherRetrofit(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
    ): Call<WeatherResponse>

    @GET("weather?")
    fun getWeatherRetrofitRx(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
    ): Single<WeatherResponse>

}