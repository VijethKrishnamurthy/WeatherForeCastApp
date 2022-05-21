package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.model.AllWeatherData
import com.example.weatherapp.util.CheckForImage
import com.example.weatherapp.databinding.FragmentItemAllDayDataBinding
import java.util.*

class ItemAdapter :ListAdapter<AllWeatherData.Hourly,ItemAdapter.ViewHolder>(HomeDiff) {

    private val calendar=Calendar.getInstance()
    object HomeDiff:DiffUtil.ItemCallback<AllWeatherData.Hourly>() {
        override fun areItemsTheSame(oldItem: AllWeatherData.Hourly, newItem: AllWeatherData.Hourly): Boolean {
            return oldItem.dt==newItem.dt
        }

        override fun areContentsTheSame(oldItem: AllWeatherData.Hourly, newItem: AllWeatherData.Hourly): Boolean {
            return oldItem==newItem
        }

    }

    inner class ViewHolder(val binding: FragmentItemAllDayDataBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(FragmentItemAllDayDataBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather=getItem(position)
        holder.binding.apply {
            imgWeather.load(CheckForImage.checkImage(weather.weather[0].icon))
            txtWeather.text=weather.weather[0].main
            txtTime.text=getTime(position)
        }
    }

    private fun getTime(position: Int):String{

        val amPm: String

        var time=calendar.get(Calendar.HOUR_OF_DAY)+position

        if (time>=24){
            time -= 24
        }

           if (time==0){
               time=12
               amPm="AM"
           }else if (time>=12 ){
               amPm=" PM"
               if(time>24){
                   time -= 24
               }
               if(time>=12){
                   time -= 12
               }
               if (time==0)
                   time=12
           }else{
               amPm=" AM"
           }

        return time.toString()+amPm
    }
}