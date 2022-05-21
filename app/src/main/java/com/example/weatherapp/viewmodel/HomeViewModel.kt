package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.AllWeatherData
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.repo.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel:ViewModel() {
    val mWeatherDetail=MutableLiveData<WeatherData>()
    val mAllWeatherDetail=MutableLiveData<AllWeatherData>()
    fun getWeatherData(lat:Float,lon:Float){
        viewModelScope.launch {
            try {
                val weather= Repository.getWeatherDetails(lat,lon)
                mWeatherDetail.postValue(weather)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
     fun getAllDayWeatherData(lat: Float,lon: Float){
         viewModelScope.launch {
             try {
                 val weather= Repository.getAllWeatherDetail(lat,lon)
                 mAllWeatherDetail.postValue(weather)
             }catch (e:Exception){
                 e.printStackTrace()
             }
         }
     }
}