package com.example.hikewise.ui.bookingopentrip.detailbooking

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.R
import com.example.hikewise.ui.bookingopentrip.BookingOpenTripViewModelFactory

class DetailOpenTripActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailOpenTripViewModel
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_open_trip)

        val name: TextView = findViewById(R.id.edtName)
        val mountainName: TextView = findViewById(R.id.edtMountainName)
        val tripName: TextView = findViewById(R.id.edtOpenTripName)
        val price: TextView = findViewById(R.id.edtPrice)

        val deleteButton = findViewById<Button>(R.id.btnDelete)

    }
}