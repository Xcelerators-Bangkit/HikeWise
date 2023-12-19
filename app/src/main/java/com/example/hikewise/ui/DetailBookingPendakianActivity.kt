package com.example.hikewise.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityDetailBookingPendakianBinding
import com.example.hikewise.model.GetTransactionViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.pref.user.UserPreference
import com.example.hikewise.pref.user.dataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailBookingPendakianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBookingPendakianBinding
    private lateinit var viewmodel: GetTransactionViewModel
    private lateinit var userPreference: UserPreference



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookingPendakianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference.getInstance(this.dataStore)
        viewmodel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(GetTransactionViewModel::class.java)

        val intents = intent
        val id = intents.getStringExtra("transaction_id")

        GlobalScope.launch {
            val emailUser = userPreference.getUserEmail.first()

            if (emailUser != null) {
                viewmodel.getTransaction(emailUser, id ?: "")
            }
        }

        viewmodel.transaction.observe(this) {
            if (it != null) {

                val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val datefromJson = LocalDateTime.parse(it.data?.date, inputFormat)
                val formattedDate = datefromJson.format(outputFormat)
                binding.nameUserBooking.text = it.data?.userName
                binding.mountainNameBooking.text = it.data?.mountainName
                binding.dateBooking.text = formattedDate
                binding.priceBooking.text = it.data?.totalPrice.toString()
                binding.totalBooking.text = it.data?.totalPrice.toString()

            }
        }

        binding.btnPay.setOnClickListener {
            val intent = Intent(this, StatusBookingPendakianActivity::class.java)
            intent.putExtra("id_payment", id)
            startActivity(intent)
        }
    }
}