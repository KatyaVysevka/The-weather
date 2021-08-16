package com.example.theweather.fragments

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.theweather.common.logDebug
import com.example.theweather.databinding.FragmentSecondBinding
import com.example.theweather.modelWeek.Week
import com.example.theweather.rest.API_WEATHER_KEY
import com.example.theweather.rest.retrofit
import com.example.theweather.resyclerView.WeatherAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.flow.collect

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding: FragmentSecondBinding get() = requireNotNull(_binding)
    private var location: MutableStateFlow<Location?>? = null
    private val adapter = WeatherAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter


        CoroutineScope(Dispatchers.IO).launch {
            location?.collect {
                if (it == null) {
                    return@collect
                }
                logDebug("locationResult in fragment 2: ${it.latitude}\t ${it.longitude}")

                retrofit
                    .getWeatherDailyRetrofit(
                        lat = it.latitude.toString(),
                        it.longitude.toString(),
                        API_WEATHER_KEY,
                        "hourly,minutely,alerts,current","metric"
                    )
                    .enqueue(object : Callback<Week> {
                        override fun onResponse(
                            call: Call<Week>,
                            response: Response<Week>
                        ) {
                            logDebug("response.code(): ${response.code()}\n")
                            logDebug("${response.body()}") // ==WeatherResponse

                            adapter.submitList(response?.body()?.daily)


                        }

                        override fun onFailure(call: Call<Week>, t: Throwable) {
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
        fun newInstance(locationResult: MutableStateFlow<Location?>) =
            SecondFragment().apply {
                location = locationResult
            }
    }
}