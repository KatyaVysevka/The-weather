package com.example.theweather.repository

import com.example.theweather.model.WeatherResponse
import com.example.theweather.rest.retrofit
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WeatherRepository  {
     fun getWeather(lat: String, lon: String, appid: String): Single<WeatherResponse> {

//         if (true){
//             return Single.error<WeatherResponse>(Throwable("response is not successfully"))
//         }

        return retrofit
            .getWeatherRetrofitRx(lat, lon, appid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                Single.just(it)
            }
    }
}