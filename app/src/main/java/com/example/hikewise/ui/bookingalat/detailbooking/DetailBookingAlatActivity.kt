package com.example.hikewise.ui.bookingalat.detailbooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.data.sewaalat.BookingEntity
import com.example.hikewise.databinding.ActivityDetailBookingAlatBinding
import com.example.hikewise.ui.MainActivity
import com.example.hikewise.ui.bookingalat.BookingViewModelFactory
import com.example.hikewise.ui.bookingopentrip.listbooking.HistoryPendakianOpenTripFragment
import com.example.hikewise.ui.fragment.HistoryPendakianFragment

class DetailBookingAlatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBookingAlatBinding
    private lateinit var viewModel: DetailBookingAlatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookingAlatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, BookingViewModelFactory.getInstance(this))[DetailBookingAlatViewModel::class.java]

        val alatId = intent.getParcelableExtra<BookingEntity>("booking")

        val equipment: TextView = binding.edtEquipment
        val name: TextView = binding.edtName
        val day: TextView = binding.edtDay
        val price: TextView = binding.edtPrice

        if (alatId != null) {
            equipment.text = alatId.alat
            name.text = alatId.name
            day.text = alatId.duration.toString()
            price.text = alatId.price.toString()
        }


        binding.btnDelete.setOnClickListener {
            alatId?.let { it1 -> viewModel.deleteBookingById(it1.id) }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}