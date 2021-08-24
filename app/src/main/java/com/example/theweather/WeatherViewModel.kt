package com.example.theweather

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.theweather.common.logDebug
import kotlinx.coroutines.flow.MutableStateFlow

//class WeatherViewModel: ViewModel() {
//    val locationResult: MutableStateFlow<Location?> = MutableStateFlow(null)
//    fun locationResult(location: Location) {
//        logDebug("locationResult: ${location.latitude}\t ${location.longitude}")
//        locationResult.value = location
//
//    }
//}