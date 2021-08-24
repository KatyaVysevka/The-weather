package com.example.theweather.modelWeek

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id") var id : Double,
    @SerializedName("name") var name : String,
    @SerializedName("coord") var coord : CoordWeek,
    @SerializedName("country") var country : String,
    @SerializedName("population") var population : Double,
    @SerializedName("timezone") var timezone : Double,
    @SerializedName("sunrise") var sunrise : Double,
    @SerializedName("sunset") var sunset : Double

)
