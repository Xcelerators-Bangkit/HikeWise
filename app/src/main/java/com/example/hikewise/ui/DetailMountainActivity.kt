package com.example.hikewise.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityDetailMountainBinding
import com.example.hikewise.response.DataItem

class DetailMountainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMountainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMountainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mountain = intent.getParcelableExtra<DataItem>("mountain")
        if (mountain != null) {
            Glide.with(this)
                .load(mountain.imageUrl)
                .into(binding.imvMountain)
            binding.tvTitleMountain.text = mountain.name
            binding.tvCountMountain.text = mountain.price.toString()
            binding.tvKetinggian.text = mountain.elevation.toString()
            binding.tvLocation.text = mountain.location
            binding.tvDescMountain.text = mountain.about
            val status = if (mountain.openStatus == true) "Open" else "Closed"
            binding.tvStatus.text = status

        }

        val detailMountain = intent.getParcelableExtra<DataItem>("detailMountain")
        if (detailMountain != null) {
            Glide.with(this)
                .load(detailMountain.imageUrl)
                .into(binding.imvMountain)
            binding.tvTitleMountain.text = detailMountain.name
            binding.tvCountMountain.text = detailMountain.price.toString()
            binding.tvKetinggian.text = detailMountain.elevation.toString()
            binding.tvLocation.text = detailMountain.location
            val status = if (detailMountain.openStatus == true) "Open" else "Closed"
            binding.tvStatus.text = status
            binding.tvDescMountain.visibility = View.GONE
        }
    }
}