package com.example.hikewise.ui.checkup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityEquipmentBinding
import com.example.hikewise.databinding.ActivityImageProcessBinding

class ImageProcessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageProcessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProcess.setOnClickListener {
            val intent = Intent(this, ResultEquipmentActivity::class.java)
            startActivity(intent)
        }
    }
}