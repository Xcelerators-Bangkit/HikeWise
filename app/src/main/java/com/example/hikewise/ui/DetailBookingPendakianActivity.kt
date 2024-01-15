package com.example.hikewise.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
import okhttp3.internal.notify
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailBookingPendakianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBookingPendakianBinding
    private lateinit var viewmodel: GetTransactionViewModel
    private lateinit var userPreference: UserPreference

    private val CHANNEL_ID = "payment_success_channel"


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

        createNotificationChannel()

        binding.btnPay.setOnClickListener {
            val intent = Intent(this, StatusBookingPendakianActivity::class.java)
            intent.putExtra("id_payment", id)
            startActivity(intent)

            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

            )

            showNotification(pendingIntent, getSoundUri(R.raw.kord))
            finish()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Payment Success"
            val descriptionText = "Notification for successful payment"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(pendingIntent: PendingIntent, soundUri: Uri) {


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(applicationInfo.icon)
            .setContentTitle("Payment Successful")
            .setContentText("Your payment has been processed successfully.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@DetailBookingPendakianActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, builder.build())
        }
    }

    private fun getSoundUri(soundResourceId: Int): Uri {
        return Uri.parse("android.resource://${packageName}/${soundResourceId}")
    }
}