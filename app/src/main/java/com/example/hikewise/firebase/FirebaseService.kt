package com.example.hikewise.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.hikewise.R
import com.example.hikewise.ui.ChatActivity
import com.example.hikewise.ui.UserActivity

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class FirebaseService : FirebaseMessagingService() {

    val CHANNEL_ID = "my_notification_channel"
    companion object{
        var sharedPref:SharedPreferences? = null

        var token:String?
            get(){
                return sharedPref?.getString("token","")
            }
            set(value){
                sharedPref?.edit()?.putString("token",value)?.apply()
            }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        token = p0
        Log.d("TOKEN", "New token generated: $token")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Log.d("FirebaseService", "Message received: ${p0.data}")

        val intent = Intent(this,UserActivity::class.java)

//        val intent = Intent(this, ChatActivity::class.java)
//        intent.putExtra("userId", p0.data["senderUserId"])
//        intent.putExtra("userName", p0.data["senderUserName"])
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }



        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,
            FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle(p0.data["title"])
            .setContentText(p0.data["message"])
            .setSmallIcon(applicationInfo.icon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId,notification)

        Log.d("FirebaseService", "Title: ${p0.data["title"]}, Message: ${p0.data["message"]}")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){

        val channelName = "ChannelFirebaseChat"
        val channel = NotificationChannel(CHANNEL_ID,channelName,IMPORTANCE_HIGH).apply {
            description="MY FIREBASE CHAT DESCRIPTION"
            enableLights(true)
            lightColor = Color.WHITE
        }
        notificationManager.createNotificationChannel(channel)

    }
}