package com.example.hikewise.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hikewise.R
import com.example.hikewise.RetrofitInstance
import com.example.hikewise.adapter.ChatAdapter
import com.example.hikewise.databinding.ActivityChatBinding
import com.example.hikewise.model.Chat
import com.example.hikewise.model.NotificationData
import com.example.hikewise.model.PushNotification
import com.example.hikewise.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.ArrayList

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var chatList = ArrayList<Chat>()
    var topic = ""
    var sender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var intent = intent
        var userId = intent.getStringExtra("userId")
        var userName = intent.getStringExtra("userName")



        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)
//        val senderName = firebaseUser!!.displayName
//        sender = senderName.toString()




        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val user = snapshot.getValue(User::class.java)
                binding.tvUserName.text = user!!.userName
                if (user.profileImage == "") {
                    binding.imgProfile.setImageResource(R.drawable.profile_image)
                } else {
                    Glide.with(this@ChatActivity).load(user.profileImage).into(binding.imgProfile)
                }
            }
        })

        binding.btnSendMessage.setOnClickListener {
            var message: String = binding.etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "message is empty", Toast.LENGTH_SHORT).show()
                binding.etMessage.setText("")
            } else {
                sendMessage(firebaseUser!!.uid, userId!!, message)
                binding.etMessage.setText("")

                // Panggil metode untuk menampilkan notifikasi jika pengguna saat ini bukan pengirim pesan
                sendNotificationIfNotSender(sender, message)
            }


//            if (message.isEmpty()) {
//                Toast.makeText(applicationContext, "message is empty", Toast.LENGTH_SHORT).show()
//                binding.etMessage.setText("")
//            } else {
//                sendMessage(firebaseUser!!.uid, userId!!, message)
//                binding.etMessage.setText("")
//                topic = "/topics/$userId"
//                PushNotification(
//                    NotificationData(userName!!, message),
//                    topic)
//                    .also {
//                        sendNotification(it)
//                    }
//            }
        }

        readMessage(firebaseUser!!.uid, userId!!)
    }

    private fun sendNotificationIfNotSender(userName: String, message: String) {
        var intent = getIntent()
        var userId = intent.getStringExtra("userId")
        var pengirim = userName

        val currentUserId = firebaseUser?.uid
        val isCurrentUserSender = currentUserId == userId

        if (!isCurrentUserSender) {
//             Jika pengguna saat ini bukan pengirim, tampilkan notifikasi
            getSenderName(firebaseUser?.uid ?: "") { senderName ->
                topic = "/topics/$userId"
                Log.d("SI PENGIRIM", senderName)

                pengirim = senderName
                sender = pengirim

                PushNotification(
                    NotificationData(pengirim, message),
                    topic
                ).also {
                    sendNotification(it)
                }
            }

//            topic = "/topics/$userId"
//            PushNotification(
//                NotificationData(userName, message),
//                topic
//            ).also {
//                sendNotification(it)
//            }
        }
    }

    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat").push().setValue(hashMap)

    }

    fun readMessage(senderId: String, receiverId: String) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()

                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)


                    if ((chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId)) ||
                        (chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId))
                    ) {
//                        chatList.add(chat)
//                        getSenderName(chat.senderId) { senderId ->
//                            chat.senderId = senderId
//                            sender = senderId
//                            Log.d("SENDER", sender)
//
//
//                        }

                        chatList.add(chat)

                    }
                }

                val chatAdapter = ChatAdapter(this@ChatActivity, chatList)

                binding.chatRecyclerView.adapter = chatAdapter
            }
        })
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d("TAG", "Response: Pesan berhasil dikirim")
            } else {
                Log.e("TAG", response.errorBody()!!.string())
            }
        } catch(e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    private fun getSenderName(senderId: String, onComplete: (String) -> Unit) {
        val userRef = FirebaseDatabase.getInstance().getReference("Users").child(senderId)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                val senderName = user?.userName ?: ""

                onComplete(senderName)

            }

            override fun onCancelled(error: DatabaseError) {
                onComplete("")
            }
        })
    }

}