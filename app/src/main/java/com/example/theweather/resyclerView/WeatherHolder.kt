package com.example.theweather.resyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.theweather.databinding.ItemBinding

class WeatherHolder(item: View): RecyclerView.ViewHolder(item){
    val binding = ItemBinding.bind(item)
    fun bind(recyclerWeather: ResyclerWeather)= with(binding){
        //тут добавить код с добавлением погоды в item
    }
}