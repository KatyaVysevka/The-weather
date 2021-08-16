package com.example.theweather.resyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theweather.R
import com.example.theweather.databinding.ItemBinding
import com.example.theweather.modelWeek.Daily
import java.text.SimpleDateFormat
import java.util.*


class WeatherAdapter : ListAdapter<Daily, WeatherAdapter.WeatherHolder>(DiffCallback()) {


    class DiffCallback : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily,
                                     newItem: Daily) = oldItem.dt == newItem.dt

        override fun areContentsTheSame(oldItem: Daily,
                                        newItem: Daily) = oldItem == newItem
    }

    class WeatherHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemBinding.bind(item)
        fun bind(daily: Daily) = with(binding) {
            textDay.text = getDayOfWeek(daily.dt)
            tvWeather13.text = daily.weather.get(0).main
            tvWeather16.text = daily.weather.get(0).main
            tvWeather19.text = daily.weather.get(0).main
            tvWeather22.text = daily.weather.get(0).main
            tvTemperature13.text = daily.temp.day.toInt().toString()
            tvTemperature16.text = daily.temp.max.toInt().toString()
            tvTemperature19.text = daily.temp.eve.toInt().toString()
            tvTemperature22.text = daily.temp.night.toInt().toString()
            ivWeather13.setImageResource(imageChoice(daily.weather.get(0).description))
            ivWeather16.setImageResource(imageChoice(daily.weather.get(0).description))
            ivWeather19.setImageResource(imageChoice(daily.weather.get(0).description))
            ivWeather22.setImageResource(imageChoice(daily.weather.get(0).description))

        }
        private fun imageChoice(description: String): Int = when (description) {
            "clear sky" -> R.drawable.ic_sun
            "few clouds" -> R.drawable.ic_cloudy_sun
            "scattered clouds" -> R.drawable.ic_cloud
            "broken clouds" -> R.drawable.ic_clouds
            "shower rain" -> R.drawable.ic_raining
            "rain" -> R.drawable.ic_rain
            "moderate rain" -> R.drawable.ic_rain
            "light rain" -> R.drawable.ic_light_rain
            "thunderstorm" -> R.drawable.ic_storm_raining
            "mist" -> R.drawable.ic_mist
            "snow" -> R.drawable.ic_snow
            else -> R.drawable.ic_exclamation_mark
        }

        //convert timestamp to day of the week
        fun getDayOfWeek(timestamp: Double): String {
            return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherAdapter.WeatherHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return WeatherHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherHolder, position: Int) {
        holder.bind(getItem(position))
    }


}