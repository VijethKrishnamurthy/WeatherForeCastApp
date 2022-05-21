package com.example.weatherapp.repo

import com.example.weatherapp.model.AllWeatherData
import com.example.weatherapp.model.WeatherData

object Repository {
    private val apiService= Network.getApiServices()

    suspend fun getWeatherDetails(lat:Float,lon:Float): WeatherData {
        return apiService.getWeatherDetail(lat,lon, Network.apikey)
    }
    suspend fun getAllWeatherDetail(lat: Float,lon: Float): AllWeatherData {
        return apiService.getAllDayWeatherDetail(lat,lon,"day", Network.apikey)
    }
}