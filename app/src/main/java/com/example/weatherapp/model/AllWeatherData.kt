package com.example.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllWeatherData(
    val hourly: List<Hourly>,
    val lat:Float,
    val lon: Float,
    val timezone: String,
    val timezone_offset: Int
):Parcelable{
    @Parcelize
    data class Hourly(
        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val feels_like: Double,
        val humidity: Int,
        val pop: Double,
        val pressure: Int,
        val temp: Double,
        val visibility: Int,
        val weather: List<WeatherXX>,
        val wind_deg: Int,
        val wind_gust: Double,
        val wind_speed: Double
    ):Parcelable
    @Parcelize
    data class WeatherXX(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    ):Parcelable

}