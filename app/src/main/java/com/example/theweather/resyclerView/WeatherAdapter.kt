package com.example.theweather.resyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theweather.R
import com.example.theweather.common.imageChoice
import com.example.theweather.common.imageChoiceNight
import com.example.theweather.common.logDebug
import com.example.theweather.databinding.ItemBinding
import com.example.theweather.modelWeek.ListWeek
import java.text.SimpleDateFormat
import java.util.*


class WeatherAdapter : ListAdapter<ListWeek, WeatherAdapter.WeatherHolder>(DiffCallback()) {


    class DiffCallback : DiffUtil.ItemCallback<ListWeek>() {
        override fun areItemsTheSame(oldItem: ListWeek,
                                     newItem: ListWeek) = oldItem.dt == newItem.dt

        override fun areContentsTheSame(oldItem: ListWeek,
                                        newItem: ListWeek) = oldItem == newItem
    }

    class WeatherHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemBinding.bind(item)
        fun bind(listWeek: ListWeek) = with(binding) {
            textDay.text = getDayOfWeek(listWeek.dt)
            logDebug("adapter: ${listWeek.dtTxt}")
            when(true){
                listWeek.dtTxt.contains("12:00:00") -> {
                    tvWeather13.text = listWeek.weather.get(0).main
                    tvTemperature13.text = listWeek.main.temp.toInt().toString()
                    ivWeather13.setImageResource(imageChoice(listWeek.weather.get(0).description))
                }
                listWeek.dtTxt.contains("15:00:00") -> {
                    tvWeather16.text = listWeek.weather.get(0).main
                    tvTemperature16.text = listWeek.main.temp.toInt().toString()
                    ivWeather16.setImageResource(imageChoice(listWeek.weather.get(0).description))
                }
                listWeek.dtTxt.contains("18:00:00") -> {
                    tvWeather19.text = listWeek.weather.get(0).main
                    tvTemperature19.text = listWeek.main.temp.toInt().toString()
                    ivWeather19.setImageResource(imageChoice(listWeek.weather.get(0).description))
                }
                listWeek.dtTxt.contains("21:00:00") -> {
                    tvWeather22.text = listWeek.weather.get(0).main
                    tvTemperature22.text = listWeek.main.temp.toInt().toString()
                    ivWeather22.setImageResource(imageChoiceNight(listWeek.weather.get(0).description))
                }

            }

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