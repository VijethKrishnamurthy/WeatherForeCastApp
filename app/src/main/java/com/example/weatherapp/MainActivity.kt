package com.example.weatherapp

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.CheckForImage
import com.example.weatherapp.viewmodel.HomeViewModel
import com.example.weatherapp.adapter.ItemAdapter
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity() {
    private val mLocationAddressCode=20
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    private val homeAdapter by lazy {
        ItemAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(checkLocationPermission()){
            getCurrentLocation()
        }else{
            getLocationPermission()
        }
        binding.rcItem.adapter=homeAdapter
        observe()
    }


    private fun observe() {
        homeViewModel.mWeatherDetail.observe(this){
            show(it)
            binding.progres.visibility= View.GONE

        }
        homeViewModel.mAllWeatherDetail.observe(this){
            Log.e("Data","Observed: $it")
            homeAdapter.submitList(it.hourly)
        }
    }

    private fun show(it: WeatherData) {
        binding.apply {
            tempTxt.text= String.format("%.2f",it.main.temp-273.15) +"\u2103"
            cityName.text=it.name
            txtCondition.text=it.weather[0].main
            tempFeel.text="feel like "+String.format("%.2f",it.main.feels_like-273.15) +"\u2103"
            windTxt.text=it.wind.speed.toString()+"km/h"
            rainTxt.text=it.main.humidity.toString()+"%"
            imgShowCondition.load(CheckForImage.checkImage(it.weather[0].icon))
        }
    }

    private fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_DENIED)
            return false

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED)
            return false

        if (ActivityCompat.checkSelfPermission(this, ACCESS_BACKGROUND_LOCATION)==PackageManager.PERMISSION_DENIED)
            return false

        return true
    }
    private fun getLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
        ),mLocationAddressCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==mLocationAddressCode){
            var flag=true
            grantResults.forEach {
                if(it==PackageManager.PERMISSION_DENIED){
                    flag=false
                    return@forEach
                }
            }
            if (flag){
                getCurrentLocation()
            }else{
                Toast.makeText(this@MainActivity,"location permission denied ",Toast.LENGTH_LONG).show()
            }
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        binding.progres.visibility= View.VISIBLE
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        locationRequest=LocationRequest.create().apply {
//            interval=TimeUnit.SECONDS.toMillis(60)
//
//            fastestInterval=TimeUnit.SECONDS.toMillis(30)
//
//            maxWaitTime=TimeUnit.MINUTES.toMillis(2)


            priority=LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback=object :LocationCallback(){

            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                p0.lastLocation.apply {
//                    Toast.makeText(this@MainActivity,"Location latitude:- ${this.latitude} longitude:- ${this.longitude}",Toast.LENGTH_LONG).show()


                    Log.e("object_form","object created ")
                    homeViewModel.getWeatherData(latitude.toFloat(),longitude.toFloat())
                    homeViewModel.getAllDayWeatherData(latitude.toFloat(),longitude.toFloat())

                }
                fusedLocationProviderClient.removeLocationUpdates(this)
            }

        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper()?:Looper.getMainLooper())

    }
}