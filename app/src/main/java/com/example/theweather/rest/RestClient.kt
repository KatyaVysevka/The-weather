package com.example.theweather.rest

import com.example.theweather.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val URL_API_MAIN = "http://api.openweathermap.org/data/2.5/"

const val API_WEATHER_KEY="815876ddaac34f06620b42ff5366f3d0"

val gson: Gson = GsonBuilder()
    .setLenient()
    .create()

fun httpLoggingInterceptor(): HttpLoggingInterceptor =
    if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else HttpLoggingInterceptor()

val client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor())
    .connectTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

val retrofit: WeatherAPI = Retrofit.Builder()
    .baseUrl(URL_API_MAIN)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(client)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
    .create(WeatherAPI::class.java)
