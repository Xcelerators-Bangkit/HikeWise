package com.example.hikewise.ui.weather

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LOGGER
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hikewise.R
import com.example.hikewise.adapter.WeatherToday
import com.example.hikewise.databinding.ActivityQuestionHealthBinding
import com.example.hikewise.databinding.ActivityResultWeatherBinding
import com.example.hikewise.pref.weatherprefs.SharedPref
import com.example.hikewise.service.weather.WeatherViewModel
import com.example.hikewise.ui.MainActivity
import com.example.hikewise.ui.checkup.ResultQuestionHealthActivity
import com.example.hikewise.utils.BaseWeather
import com.example.weatherapp.ForeCast
import com.example.weatherapp.WeatherList
import java.util.Locale

class ResultWeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultWeatherBinding
    private lateinit var viewmodel: WeatherViewModel
    private lateinit var adapter: WeatherToday
    private lateinit var rvForecast: RecyclerView
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        adapter = WeatherToday()
        rvForecast = binding.rvForecast
        rvForecast.layoutManager = LinearLayoutManager(this)

        val sharedPref = SharedPref.getInstance(this@ResultWeatherActivity)
        val city = sharedPref.getValueOrNull("city").toString()

        viewmodel.getWeather(city)
        binding.tvCity.text = city

        viewmodel.todayWeatherLiveData.observe(this) {
            val setList = it as List<WeatherList>

            adapter.setList(setList)
            adapter.notifyDataSetChanged()

            rvForecast.adapter = adapter
        }
        viewmodel.closetorexactlysameweatherdata.observe(this) {
            val temperatureFahrenheit = it!!.main?.temp
            val temperatureCelsius = (temperatureFahrenheit?.minus(273.15))
            val temperatureFormatted = String.format("%.2f", temperatureCelsius)

            for (i in it.weather) {
                binding.tvCondition.text = i.description
            }

            binding.tvSuhu.text = "$temperatureFormatted °C"
            binding.tvHumidityValue.text = it.main?.humidity.toString()
            binding.tvWindSpeedValue.text = it.wind?.speed.toString()

            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = inputFormat.parse(it.dtTxt!!)
            val outputFormat = SimpleDateFormat("d MMMM EEEE", Locale.getDefault())
            val dateanddayname = outputFormat.format(date!!)

            binding.tvDate.text = dateanddayname
            binding.tvPercentRain.text = "${it.pop.toString()}%"

            for (i in it.weather) {
                if (i.icon == "01d") {
                    binding.tvImageWeather.setImageResource(R.drawable.quill_sun_white)
                }
                if (i.icon == "01n") {
                    binding.tvImageWeather.setImageResource(R.drawable.quill_moon_white)
                }
                if (i.icon == "02d") {
                    binding.tvImageWeather.setImageResource(R.drawable.icon_weather_line_white)
                }
                if (i.icon == "02n") {
                    binding.tvImageWeather.setImageResource(R.drawable.icon_weather_line_white)
                }
                if (i.icon == "03d") {
                    binding.tvImageWeather.setImageResource(R.drawable.icon_weather_line_white)
                }
                if (i.icon == "03n") {
                    binding.tvImageWeather.setImageResource(R.drawable.icon_weather_line_white)
                }
                if (i.icon == "04d") {
                    binding.tvImageWeather.setImageResource(R.drawable.icon_weather_line_white)
                }
                if (i.icon == "04n") {
                    binding.tvImageWeather.setImageResource(R.drawable.icon_weather_line_white)
                }
                if (i.icon == "09d") {
                    binding.tvImageWeather.setImageResource(R.drawable.icon_weather_line_white)
                }
                if (i.icon == "09n") {
                    binding.tvImageWeather.setImageResource(R.drawable.icon_weather_line_white)
                }

            }

        }



//        if (checkLocationPermission()) {
//            getCurrentLocation()
//        } else {
//            requestLocationPermission()
//        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val query = searchView.editText.text.toString()
                    val pref = SharedPref.getInstance(this@ResultWeatherActivity)
                    pref.setValueOrNull("city", query)
                    viewmodel.getWeather(query)
                    binding.tvCity.text = query
                    searchView.hide()
                    false
                }
        }
    }

    private fun checkLocationPermission(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED && coarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            BaseWeather.PERMISSION_REQUEST_CODE
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == BaseWeather.PERMISSION_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED){

                getCurrentLocation()
            } else {

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            val location: Location? =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null) {


                val latitude = location.latitude
                val longitude = location.longitude
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

                if (!addresses.isNullOrEmpty()) {
                    val city = addresses[0].locality
                    binding.tvCity.text = city
                    val myPrefs = SharedPref.getInstance(this)
                    myPrefs.setValue("lon", longitude.toString())
                    myPrefs.setValue("lat", latitude.toString())
                    viewmodel.getWeather()
                }


//                Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_SHORT).show()
//                Log.d("Current Location", "Latitude: $latitude, Longitude: $longitude")
            } else {

            }
        } else {

        }
    }
}