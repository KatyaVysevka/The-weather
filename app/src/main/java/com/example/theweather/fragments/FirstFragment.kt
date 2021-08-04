package com.example.theweather.fragments

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.theweather.common.logDebug
import com.example.theweather.databinding.FragmentFirstBinding
import com.example.theweather.model.WeatherResponse
import com.example.theweather.rest.API_WEATHER_KEY
import com.example.theweather.rest.retrofit
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding: FragmentFirstBinding get() = requireNotNull(_binding)
    private var location: MutableStateFlow<Location?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logDebug("onViewCreated")

        CoroutineScope(Dispatchers.IO).launch {
            location?.collect {
                if (it == null) {
                    return@collect
                }
                logDebug("locationResult in fragment: ${it.latitude}\t ${it.longitude}")

                retrofit
                    .getWeatherRetrofit(
                        lat = it.latitude.toString(),
                        it.longitude.toString(),
                        API_WEATHER_KEY,
                        "metric"
                    )
                    .enqueue(object : Callback<WeatherResponse> {
                        override fun onResponse(
                            call: Call<WeatherResponse>,
                            response: Response<WeatherResponse>
                        ) {
                            logDebug("response.code(): ${response.code()}\n")
                            logDebug("${response.body()}") // ==WeatherResponse

                            binding.apply {
                                temperature.text = response.body()?.main?.temp

                            }

                        }

                        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                            logDebug("onFailure: ${t.message}")
                        }
                    })
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(locationResult: MutableStateFlow<Location?>) = FirstFragment().apply {
            logDebug("newInstance")
            location = locationResult
        }
    }

}