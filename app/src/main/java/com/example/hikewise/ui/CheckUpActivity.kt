package com.example.hikewise.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityCheckUpBinding
import com.example.hikewise.databinding.ActivityLoginBinding
import com.example.hikewise.ui.checkup.EquipmentActivity
import com.example.hikewise.ui.checkup.HealthActivity

class CheckUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardEquipment.setOnClickListener{
            val intent = Intent(this, EquipmentActivity::class.java)
            startActivity(intent)
        }

        binding.cardHealth.setOnClickListener{
            val intent = Intent(this, HealthActivity::class.java)
            startActivity(intent)
        }
    }
}