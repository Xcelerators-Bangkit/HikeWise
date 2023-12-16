package com.example.hikewise.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hikewise.R
import com.example.hikewise.adapter.AllMountainAdapter
import com.example.hikewise.databinding.ActivitySearchMountainBinding
import com.example.hikewise.model.GetAllMountainViewModel
import com.example.hikewise.model.ViewModelFactory

class SearchMountainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMountainBinding
    private lateinit var mountain : GetAllMountainViewModel
    private lateinit var adapter: AllMountainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMountainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mountain = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(GetAllMountainViewModel::class.java)


        mountain.getAllMountain()
        mountain.mountain.observe(this) {
            adapter = AllMountainAdapter()
            binding.rvAllMountain.layoutManager = GridLayoutManager(this, 2)
            binding.rvAllMountain.adapter = adapter
            binding.rvAllMountain.setHasFixedSize(true)
            adapter.submitList(it)
        }
    }
}