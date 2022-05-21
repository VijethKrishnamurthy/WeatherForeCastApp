package com.example.weatherapp.util

import com.example.weatherapp.R


object CheckForImage {
   fun checkImage(code:String):Int{
        when(code){
            "01d"->return R.drawable.img_sun
            "02d"->return R.drawable.cloud_sun
            "03d"->return R.drawable.cloud
            "04d"->return R.drawable.cloud
            "09d"->return R.drawable.rainday
            "10d"->return R.drawable.rain
            "11d"->return R.drawable.thunderd
            "13d"->return R.drawable.snow
            "50d"->return R.drawable.daywind
            "01n"->return R.drawable.night
            "02n"->return R.drawable.cloud_night
            "03n"->return R.drawable.cloud
            "04n"->return R.drawable.cloud
            "09n"->return R.drawable.night_rain
            "10n"->return R.drawable.night_rain
            "11n"->return R.drawable.thunderd
            "13n"->return R.drawable.snow
            "50n"->return R.drawable.night_wind
        }
        return R.drawable.cloud
    }
}