package com.example.hikewise.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityBookingBinding

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            cvPendakian.setOnClickListener {
                val intent = Intent(this@BookingActivity, BookingPendakianActivity::class.java)
                startActivity(intent)
                finish()
            }
            cvBookingAlat.setOnClickListener {
                val intent = Intent(this@BookingActivity, AlatCampingActivity::class.java)
                startActivity(intent)
                finish()
            }
            cvOpentrip.setOnClickListener {
                val intent = Intent(this@BookingActivity, BookingOpenTripActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}