package com.example.hikewise.service.weather

import com.example.hikewise.utils.BaseWeather
import com.example.weatherapp.ForeCast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("forecast?")
    fun getCurrentWeather(
        @Query("lat")
        lat: String,
        @Query("lon")
        lon: String,
        @Query("appid")
        appid: String = BaseWeather.API_KEY
    ) : Call<ForeCast>


    @GET("forecast?")
    fun getWeatherByCity(
        @Query("q")
        city: String,
        @Query("appid")
        appid: String = BaseWeather.API_KEY
    ) : Call<ForeCast>
}