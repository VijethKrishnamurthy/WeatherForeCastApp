package com.example.weatherapp.repo

import com.example.weatherapp.apiservices.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    private const val BASIC_URL="https://api.openweathermap.org"
    const  val apikey= "1f63ba67a6282c1659e4cf5ff1c0fd6b"
    fun getApiServices(): ApiService {
        val logging=HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client:OkHttpClient=OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASIC_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}
