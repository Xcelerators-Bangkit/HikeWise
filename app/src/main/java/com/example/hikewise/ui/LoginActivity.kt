package com.example.hikewise.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityLoginBinding
import com.example.hikewise.model.LoginViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.pref.user.User
import com.example.hikewise.pref.user.UserPreference
import com.example.hikewise.pref.user.dataStore
import com.example.hikewise.response.LoginResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewmodel: LoginViewModel
    private lateinit var userPreference: UserPreference

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animation()
        spanCustom()

        userPreference = UserPreference.getInstance(this.dataStore)
        viewmodel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(LoginViewModel::class.java)

        auth = FirebaseAuth.getInstance()

        viewmodel.login.observe(this) {response ->
            if (response != null) {
                Log.d("LoginActivity", response.message())
            }

        }

        binding.btnLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            login(email, password)
        }

    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                viewmodel.login(LoginResponse(
                    email,
                    password
                ))
                GlobalScope.launch {
                    userPreference.saveUser(User(email, password))
                }
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Login Failed")
                dialog.setMessage(it.exception?.message)
                dialog.setPositiveButton("OK") { _, _ ->

                }
                dialog.show()
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.Q)
    private fun spanCustom(){
        val registerText = binding.donthaveaccount

        val span = SpannableString(getString(R.string.register))
        val styleSpan = StyleSpan(android.graphics.fonts.FontStyle.FONT_WEIGHT_BOLD)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        span.setSpan(clickableSpan, 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(styleSpan, 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        registerText.text = TextUtils.concat("Don't have an account? ", span)
        registerText.movementMethod = LinkMovementMethod.getInstance()
    }

    @SuppressLint("Recycle")
    private fun animation(){
        val heading = ObjectAnimator.ofFloat(binding.heading, View.ALPHA, 1f).setDuration(700)
        val textEmail = ObjectAnimator.ofFloat(binding.textViewEmail, View.ALPHA, 1f).setDuration(700)
        val textPassword = ObjectAnimator.ofFloat(binding.textViewPassword, View.ALPHA, 1f).setDuration(700)
        val email = ObjectAnimator.ofFloat(binding.emailTextLayout, View.ALPHA, 1f).setDuration(700)
        val password = ObjectAnimator.ofFloat(binding.passwordTextLayout, View.ALPHA, 1f).setDuration(700)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(700)
        val register = ObjectAnimator.ofFloat(binding.donthaveaccount, View.ALPHA, 1f).setDuration(700)


        AnimatorSet().apply {
            playSequentially(heading, textEmail, email, textPassword, password, login, register)
            start()
        }
    }
}




