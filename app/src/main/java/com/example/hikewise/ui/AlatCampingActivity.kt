package com.example.hikewise.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.adapter.AlatCampingAdapter
import com.example.hikewise.data.sewaalat.SewaAlat
import com.example.hikewise.databinding.ActivityAlatCampingBinding

class AlatCampingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlatCampingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlatCampingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = AlatCampingAdapter()
        val recyclerView = binding.rvAlatCamping
        recyclerView.adapter = adapter
        recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)

        adapter.submitList(SewaAlat.sewaalat)


    }
}