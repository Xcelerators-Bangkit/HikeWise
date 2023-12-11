package com.example.hikewise.ui.bookingopentrip.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.R
import com.example.hikewise.data.opentrip.OpenTripEntity
import com.example.hikewise.databinding.ActivityBookingOpenTripBinding
import com.example.hikewise.ui.MainActivity
import com.example.hikewise.ui.bookingalat.BookingViewModelFactory
import com.example.hikewise.ui.bookingopentrip.BookingOpenTripViewModelFactory

class BookingOpenTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingOpenTripBinding
    private lateinit var viewModel: BookingOpenTripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingOpenTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, BookingOpenTripViewModelFactory.getInstance(this))[BookingOpenTripViewModel::class.java]

        binding.btnBooking.setOnClickListener {
            val name = binding.edtName.text.toString()
            val nameTrip = binding.edtOpenTripName.text.toString()
            val nameMountain = binding.edtMountainName.text.toString()
            val price = binding.edtPrice.text.toString().toInt()

            viewModel.bookingOpenTrip(OpenTripEntity(
                name = name,
                tripName = nameTrip,
                mountainName = nameMountain,
                price = price
            ))

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}