package com.example.hikewise.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityStatusBookingPendakianBinding
import com.example.hikewise.model.GetTransactionViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.pref.user.UserPreference
import com.example.hikewise.pref.user.dataStore
import com.example.hikewise.ui.home.HomeFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StatusBookingPendakianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatusBookingPendakianBinding
    private lateinit var viewModel: GetTransactionViewModel
    private lateinit var userPreference: UserPreference
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusBookingPendakianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference.getInstance(this.dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(GetTransactionViewModel::class.java)

        statusPayment()

        detailPayment()



        binding.btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun statusPayment(){
        val intents = intent
        val id = intents.getStringExtra("id_payment")

        GlobalScope.launch {
            val emailUser = userPreference.getUserEmail.first()

            viewModel.getTransaction(
                emailUser ?: "",
                id ?: ""
            )
        }

        viewModel.transaction.observe(this) {
            if (it != null) {

                val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val datefromJson = LocalDateTime.parse(it.data?.date, inputFormat)
                val formattedDate = datefromJson.format(outputFormat)

                binding.tvStatusPayment.text = "Payment ${it.message.toString()}"
                binding.idBooking.text = it.data?.id
                binding.nameUserBookingStatus.text = it.data?.userName
                binding.mountainNameBookingStatus.text = it.data?.mountainName
                binding.dateUserBookingStatus.text = formattedDate
                binding.priceBookingStatus.text = it.data?.totalPrice.toString()

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun detailPayment(){
        val intenst = intent
        val idPayment = intenst.getStringExtra("payment_detail")

        GlobalScope.launch {
            val emailUser = userPreference.getUserEmail.first()

            if (emailUser != null) {
                viewModel.getTransaction(emailUser, idPayment ?: "")
            }
        }

        viewModel.transaction.observe(this) {
            if (it != null) {

                val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val datefromJson = LocalDateTime.parse(it.data?.date, inputFormat)
                val formattedDate = datefromJson.format(outputFormat)

                binding.tvStatusPayment.text = it.message.toString()
                binding.idBooking.text = it.data?.id
                binding.nameUserBookingStatus.text = it.data?.userName
                binding.mountainNameBookingStatus.text = it.data?.mountainName
                binding.dateUserBookingStatus.text = formattedDate
                binding.priceBookingStatus.text = it.data?.totalPrice.toString()
            }
        }
    }
}