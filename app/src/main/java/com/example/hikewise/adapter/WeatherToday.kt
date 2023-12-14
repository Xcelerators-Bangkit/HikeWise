package com.example.hikewise.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hikewise.R
import com.example.weatherapp.WeatherList
import java.text.SimpleDateFormat
import java.util.Calendar

class WeatherToday : RecyclerView.Adapter<WeatherToday.TodayViewHolder>() {

    private var listOfTodayWeather = listOf<WeatherList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return TodayViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {

        val todayForecast = listOfTodayWeather[position]

        holder.timeDisplay.text = todayForecast.dtTxt!!.substring(11, 16).toString()

        val temperatureFahrenheit = todayForecast.main?.temp
        val temperatureCelsius = (temperatureFahrenheit?.minus(273.15))
        val temperatureFormatted = String.format("%.2f", temperatureCelsius)

        holder.tempDisplay.text = "$temperatureFormatted °C"

        val calendar = Calendar.getInstance()

        val dateFormat = SimpleDateFormat("HH:mm")
        val formattedTime = dateFormat.format(calendar.time)

        val timeofapi = todayForecast.dtTxt!!.split("")
        val partafterspace = timeofapi[1]

        Log.e("time", "formaated time:${formattedTime}, timetoapi:${partafterspace}")

        for (i in todayForecast.weather) {

            if (i.icon == "01d") {
                holder.imageDisplay.setImageResource(R.drawable.quill_sun_white)
            }
            if (i.icon == "01n") {
                holder.imageDisplay.setImageResource(R.drawable.quill_moon_white)
            }
            if (i.icon == "02d") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "02n") {
                holder.imageDisplay.setImageResource(R.drawable.carbon_humidity_alt)
            }
            if (i.icon == "03d") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "03n") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "04d") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "04n") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "09d") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "09n") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "10d") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "10n") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "11d") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "11n") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "13d") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "13n") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "50d") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }
            if (i.icon == "50n") {
                holder.imageDisplay.setImageResource(R.drawable.icon_weather_line_white)
            }

        }

    }


    fun setList(listOfToday : List<WeatherList>){
        this.listOfTodayWeather = listOfToday
    }

    override fun getItemCount(): Int {
        return listOfTodayWeather.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageDisplay : ImageView = itemView.findViewById(R.id.tv_image_weather_forecast)
        val tempDisplay : TextView = itemView.findViewById(R.id.tv_suhu_forecast)
        val timeDisplay : TextView = itemView.findViewById(R.id.tv_time_forecast)
    }

}