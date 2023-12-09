package com.example.hikewise.ui.checkup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityCheckUpBinding
import com.example.hikewise.databinding.ActivityHealthBinding

class HealthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHealthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, QuestionHealthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}