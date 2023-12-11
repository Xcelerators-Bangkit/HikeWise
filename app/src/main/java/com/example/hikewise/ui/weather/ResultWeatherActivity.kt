package com.example.hikewise.ui.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityQuestionHealthBinding
import com.example.hikewise.databinding.ActivityResultWeatherBinding
import com.example.hikewise.ui.MainActivity
import com.example.hikewise.ui.checkup.ResultQuestionHealthActivity

class ResultWeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}