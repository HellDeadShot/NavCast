package com.helldeadshot.navcast.data.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") val coord: Coordinates,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("name") val name: String,
    @SerializedName("sys") val sys: Sys?
)

data class Weather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("feels_like") val feelsLike: Double? = null
)

data class Coordinates(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)

data class Wind(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Int?
)

data class Sys(
    @SerializedName("country") val country: String?
)
