package com.example.theweather.fragments

import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.example.theweather.R
import com.example.theweather.common.logDebug
import com.example.theweather.databinding.FragmentFirstBinding
import com.example.theweather.model.WeatherResponse
import com.example.theweather.rest.API_WEATHER_KEY
import com.example.theweather.rest.retrofit
import com.google.android.libraries.places.internal.it
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
                                temperature.text = response.body()?.main?.temp?.toInt().toString()
                                textWeather.text = response.body()?.weather?.get(0)?.main.toString()
                                airPressure.text = response.body()?.main?.pressure?.toString() + " hPa"
                                chanceOfRain.text = response.body()?.main?.humidity.toString() + "%"
                                wet.text = response.body()?.main?.grndLevel.toString() + "mm"           // тут какаято -дичь
                                windSpeed.text = response.body()?.wind?.speed.toString() + " km/h" // тут метры в секунду. перевести в км в час. в другом приложении посмотрела, вроде км в час
                                directionWind.text = "SE"  //там просто такого нет
                                location.text = response.body()?.name
                                imWeather.setImageResource(imageChoice(response.body()?.weather?.get(0)?.description.toString()
                                    )
                                )
                            }
                            fun generateReport(): String {
                                var text ="Сегодня в " + response.body()?.name + ". " + response.body()?.weather?.get(0)?.main.toString() +
                                        "\nТемпература воздуха:" + response.body()?.main?.temp?.toInt().toString() +
                                        "\nОтносительная влажность:" + response.body()?.main?.humidity.toString() + "%" +
                                        "\nСкорость ветра:" + response.body()?.wind?.speed.toString() + " km/h"

                                return text
                            }

                            binding.share.setOnClickListener{
                                val intent = Intent(Intent.ACTION_SEND)
                                intent.type = "text/plain"
                                intent.putExtra(
                                    Intent.EXTRA_TEXT,
                                    generateReport()
                                )
                                val chooserTitle = "Погода на сегодня"
                                val chosenIntent = Intent.createChooser(intent, chooserTitle)
                                startActivity(chosenIntent)
                            }

                        }

                        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                            logDebug("onFailure: ${t.message}")
                        }
                    })
            }
        }
    }



    private fun imageChoice(description: String): Int = when (description) {
        "clear sky" -> R.drawable.ic_sun
        "few clouds" -> R.drawable.ic_cloudy_sun
        "scattered clouds" -> R.drawable.ic_cloud
        "broken clouds" -> R.drawable.ic_clouds
        "shower rain" -> R.drawable.ic_raining
        "rain" -> R.drawable.ic_rain
        "light rain" -> R.drawable.ic_light_rain
        "thunderstorm" -> R.drawable.ic_storm_raining
        "mist" -> R.drawable.ic_mist
        "snow" -> R.drawable.ic_snow
        else -> R.drawable.ic_exclamation_mark
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