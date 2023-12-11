package com.example.hikewise.ui.checkup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityResultEquipmentBinding

class ResultEquipmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultEquipmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultEquipmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}