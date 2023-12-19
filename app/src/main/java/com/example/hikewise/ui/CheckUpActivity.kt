package com.example.hikewise.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityCheckUpBinding
import com.example.hikewise.databinding.ActivityLoginBinding
import com.example.hikewise.ui.checkup.EquipmentActivity
import com.example.hikewise.ui.checkup.HealthActivity
import com.example.hikewise.ui.checkup.ImageProcessActivity
import com.example.hikewise.ui.checkup.QuestionHealthActivity

class CheckUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alertHealth.setOnClickListener {
            binding.alertHealth.visibility = android.view.View.GONE
        }

        binding.alertCheckequipment.setOnClickListener {
            binding.alertCheckequipment.visibility = android.view.View.GONE
        }

        binding.cardEquipment.setOnClickListener{
            binding.alertCheckequipment.visibility = android.view.View.VISIBLE
            binding.btNextEquipment.setOnClickListener {
                val intent = Intent(this, ImageProcessActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.cardHealth.setOnClickListener{
            binding.alertHealth.visibility = android.view.View.VISIBLE
            binding.btNextHealth.setOnClickListener {
                val intent = Intent(this, QuestionHealthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}