package com.example.hikewise.ui.bookingopentrip.detailbooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.data.opentrip.OpenTripEntity
import com.example.hikewise.databinding.ActivityDetailOpenTripBinding
import com.example.hikewise.ui.bookingopentrip.BookingOpenTripViewModelFactory
import com.example.hikewise.ui.bookingopentrip.listbooking.HistoryPendakianOpenTripFragment

class DetailOpenTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOpenTripBinding
    private lateinit var viewModel: DetailOpenTripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOpenTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, BookingOpenTripViewModelFactory.getInstance(this))[DetailOpenTripViewModel::class.java]

        val openTripId = intent.getParcelableExtra<OpenTripEntity>("detailOpenTrip")

        val name: TextView = binding.edtName
        val price: TextView = binding.edtPrice
        val openTripName: TextView = binding.edtOpenTripName
        val mountainName: TextView = binding.edtMountainName

        if (openTripId != null) {
            name.text = openTripId.name
            price.text = openTripId.price.toString()
            openTripName.text = openTripId.tripName
            mountainName.text = openTripId.mountainName
        }


        binding.btnDelete.setOnClickListener {
            openTripId?.let { it1 -> viewModel.deleteOpenTripById(it1.id) }
            val intent = Intent(this, HistoryPendakianOpenTripFragment::class.java)
            startActivity(intent)
            finish()
        }

    }
}