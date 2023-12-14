package com.example.hikewise.service.weather

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.MyApplication
import com.example.hikewise.pref.weatherprefs.SharedPref
import com.example.weatherapp.WeatherList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherViewModel : ViewModel() {

    val todayWeatherLiveData = MutableLiveData<List<WeatherList>>()
    val forecastWeatherLiveData = MutableLiveData<List<WeatherList>>()

    val closetorexactlysameweatherdata = MutableLiveData<WeatherList?>()
    val cityName = MutableLiveData<String?>()
    val context = MyApplication.instance

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeather(city: String? = null) = viewModelScope.launch(Dispatchers.IO) {
        val todayWeatherList = mutableListOf<WeatherList>()

        val currentDateData = LocalDateTime.now()
        val currentDataPattern = currentDateData.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val sharedPrefs = SharedPref.getInstance(context)
        val lat = sharedPrefs.getValue("lat").toString()
        val lon = sharedPrefs.getValue("lon").toString()

        val call = if (city!=null) {
            RetrofitInstance.api.getWeatherByCity(city)
        } else {
            RetrofitInstance.api.getCurrentWeather(lat, lon)
        }

        val response = call.execute()
        if (response.isSuccessful) {
            val weatherList = response.body()?.weatherList
            cityName.postValue(response.body()?.city!!.name)

            val presentDate = currentDataPattern

            weatherList?.forEach { weather ->
                if (weather.dtTxt!!.split("\\s".toRegex()).contains(presentDate)) {
                    todayWeatherList.add(weather)
                }
            }

            val closestWeather = findClosestWeather(todayWeatherList)
            closetorexactlysameweatherdata.postValue(closestWeather)
            todayWeatherLiveData.postValue(todayWeatherList)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getForecastUpComing(city: String? = null) = viewModelScope.launch(Dispatchers.IO) {
        val foreCastWeatherList = mutableListOf<WeatherList>()

        val currentDateData = LocalDateTime.now()
        val currentDataPattern = currentDateData.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val sharedPrefs = SharedPref.getInstance(context)
        val lat = sharedPrefs.getValue("lat").toString()
        val lon = sharedPrefs.getValue("lon").toString()

        val call = if (city!=null) {
            RetrofitInstance.api.getWeatherByCity(city)
        } else {
            RetrofitInstance.api.getCurrentWeather(lat, lon)
        }

        val response = call.execute()
        if (response.isSuccessful) {
            val weatherList = response.body()?.weatherList
            cityName.postValue(response.body()?.city!!.name)

            val presentDate = currentDataPattern

            weatherList?.forEach { weather ->
                if (weather.dtTxt!!.split("\\s".toRegex()).contains(presentDate)) {
                    if (weather.dtTxt!!.substring(11, 16) == "12:00") {
                        foreCastWeatherList.add(weather)
                        Log.d("TAG", "getForecastUpComing: ${weather.dtTxt}")
                    }

                }
            }

            forecastWeatherLiveData.postValue(foreCastWeatherList)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun findClosestWeather(weatherList: List<WeatherList>): WeatherList? {
        val systemTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        var closestWeather: WeatherList? = null
        var minTimeDifference = Long.MAX_VALUE

        for (weather in weatherList) {
            val weatherTime = weather.dtTxt!!.substring(11, 16)
            val timeDifference = Math.abs(timeToMinutes(weatherTime) - timeToMinutes(systemTime))

            if (timeDifference < minTimeDifference) {
                minTimeDifference = timeDifference.toLong()
                closestWeather = weather
            }
        }

        return closestWeather
    }

    private fun timeToMinutes(time: String): Int {
        val parts = time.split(":")
        return parts[0].toInt() * 60 + parts[1].toInt()
    }


}