package com.example.hikewise.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.fonts.FontStyle
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.annotation.RequiresApi
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spanCustom()

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            register(email, password)

        }

    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Register Success")
                    dialog.setMessage("You have successfully registered")
                    dialog.setPositiveButton("OK") { _, _ ->
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    dialog.show()
                } else {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("Register Failed")
                    dialog.setMessage(it.exception?.message)
                    dialog.setPositiveButton("OK") { _, _ ->

                    }
                    dialog.show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun spanCustom(){
        val registerText = binding.doyouhaveaccount

        val span = SpannableString(getString(R.string.login))
        val styleSpan = StyleSpan(FontStyle.FONT_WEIGHT_BOLD)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        span.setSpan(clickableSpan, 0 , span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(styleSpan, 0 , span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        registerText.text = TextUtils.concat("Do you have an account? ", span)
        registerText.movementMethod = LinkMovementMethod.getInstance()
    }
}

