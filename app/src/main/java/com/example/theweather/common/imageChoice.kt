package com.example.theweather.common

import com.example.theweather.R

fun imageChoice(description: String): Int = when (description) {
    "clear sky" -> R.drawable.ic_sun
    "few clouds" -> R.drawable.ic_cloudy_sun
    "scattered clouds", "overcast clouds" -> R.drawable.ic_cloud
    "broken clouds" -> R.drawable.ic_clouds
    "shower rain", "heavy intensity rain", "very heavy rain", "extreme rain",
    "light intensity shower rain", "heavy intensity shower rain", "ragged shower rain" -> R.drawable.ic_raining
    "rain", "moderate rain" -> R.drawable.ic_rain
    "light rain" -> R.drawable.ic_light_rain
    "thunderstorm" -> R.drawable.ic_storm_raining
    "mist" -> R.drawable.ic_mist
    "snow", "freezing rain", "light snow" -> R.drawable.ic_snow
    else -> R.drawable.ic_exclamation_mark
}

fun imageChoiceNight(description: String): Int = when (description) {
    "clear sky" -> R.drawable.ic_moon
    "few clouds" -> R.drawable.ic_night
    "scattered clouds", "overcast clouds" -> R.drawable.ic_cloud
    "broken clouds" -> R.drawable.ic_clouds
    "shower rain", "heavy intensity rain", "very heavy rain", "extreme rain",
    "light intensity shower rain", "heavy intensity shower rain", "ragged shower rain" -> R.drawable.ic_raining
    "rain", "moderate rain" -> R.drawable.ic_rain
    "light rain" -> R.drawable.ic_light_rain
    "thunderstorm" -> R.drawable.ic_storm_raining
    "mist" -> R.drawable.ic_mist
    "snow", "freezing rain", "light snow" -> R.drawable.ic_snow
    else -> R.drawable.ic_exclamation_mark
}