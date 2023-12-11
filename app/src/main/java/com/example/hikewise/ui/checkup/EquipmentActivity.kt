package com.example.hikewise.ui.checkup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityCheckUpBinding
import com.example.hikewise.databinding.ActivityEquipmentBinding

class EquipmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEquipmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, ImageProcessActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}