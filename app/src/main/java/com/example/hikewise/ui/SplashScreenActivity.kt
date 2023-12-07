package com.example.hikewise.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Fade
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivitySplashScreenBinding
import com.example.hikewise.pref.ThemePreference
import com.example.hikewise.pref.ThemeViewModel
import com.example.hikewise.pref.ThemeViewModelFactory
import com.example.hikewise.pref.dataStore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var mainViewModel: ThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = ThemePreference.getInstance(this@SplashScreenActivity.dataStore)
        mainViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }


        val fade = Fade()
        window.enterTransition = fade
        window.exitTransition = fade
        Handler().postDelayed({
            val user = Firebase.auth.currentUser
            val intent = if (user != null) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, WelcomeActivity::class.java)
            }
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }, 3000)
    }
}