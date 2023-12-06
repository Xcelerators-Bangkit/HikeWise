package com.example.hikewise.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val iconMap = mapOf(
        R.id.menu_home to Pair(R.drawable.icon_home_line, R.drawable.icon_home_fill),
        R.id.menu_weather to Pair(R.drawable.icon_weather_line, R.drawable.icon_weather_fill),
        R.id.menu_history to Pair(R.drawable.icon_calendar_line, R.drawable.icon_calendar_fill),
        R.id.menu_profile to Pair(R.drawable.icon_person_line, R.drawable.icon_person_fill)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView : BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)


        navView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.menu_home -> {
                    navController.navigate(R.id.navigation_home)
                    updateIcon(menuItem.itemId)
                    true
                }
                R.id.menu_weather -> {
                    navController.navigate(R.id.navigation_weather)
                    updateIcon(menuItem.itemId)
                    true
                }
                R.id.menu_history -> {
                    navController.navigate(R.id.navigation_history)
                    updateIcon(menuItem.itemId)
                    true
                }
                R.id.menu_profile -> {
                    navController.navigate(R.id.navigation_profile)
                    updateIcon(menuItem.itemId)
                    true
                }
                else -> false
            }
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateIcon(iconResId: Int) {
        iconMap.forEach { (id, pair) ->
            val menuItem = binding.bottomNavigationView.menu.findItem(id)
            if (id == iconResId) {
                menuItem.icon = getDrawable(pair.second)
            } else {
                menuItem.icon = getDrawable(pair.first)
            }
        }
    }
}