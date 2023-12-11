package com.example.hikewise.ui.bookingalat.booking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.data.sewaalat.AlatCamping
import com.example.hikewise.data.sewaalat.BookingEntity
import com.example.hikewise.databinding.ActivityBookingAlatBinding
import com.example.hikewise.ui.MainActivity
import com.example.hikewise.ui.bookingalat.BookingViewModelFactory

class BookingAlatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingAlatBinding
    private lateinit var viewModel: BookingAlatViewModel
    private lateinit var alatCamping: AlatCamping
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingAlatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@BookingAlatActivity, BookingViewModelFactory.getInstance(this))[BookingAlatViewModel::class.java]

        alatCamping = intent.getParcelableExtra("alat") ?: AlatCamping(0, "", 0, 0)

        // Sekarang, Anda dapat menggunakan objek alatCamping sesuai kebutuhan
        // Misalnya, menampilkan informasi alatCamping di tampilan
        binding.imageSewaAlat.setImageResource(alatCamping.image)
        binding.nameAlat.text = alatCamping.name
        binding.priceSewaAlat.text = "Price: ${alatCamping.price} IDR"

        binding.btSewaAlat.setOnClickListener {
            val name = binding.editTextPersonName.text.toString()
            val day = binding.editTextDay.text.toString().toInt()

            viewModel.booking(BookingEntity(
                name = name,
                alat = alatCamping.name,
                duration = day,
                price = alatCamping.price,
                image = alatCamping.image
            ))

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

    }



}