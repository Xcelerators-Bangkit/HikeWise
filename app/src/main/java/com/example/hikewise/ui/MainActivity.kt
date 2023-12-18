package com.example.hikewise.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityMainBinding
import com.example.hikewise.model.GetUserDetailViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.pref.ThemePreference
import com.example.hikewise.pref.ThemeViewModel
import com.example.hikewise.pref.ThemeViewModelFactory
import com.example.hikewise.pref.dataStore
import com.example.hikewise.pref.user.UserPreference
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: ThemeViewModel
    private lateinit var viewModel: GetUserDetailViewModel
    private lateinit var userPreference: UserPreference

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

        userPreference = UserPreference.getInstance(this.dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(GetUserDetailViewModel::class.java)
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

        GlobalScope.launch {
            val emailUser = userPreference.getUserEmail.first()
            if (emailUser != null) {
                viewModel.getUserDetail(emailUser)
            }
        }

        viewModel.userDetail.observe(this) { user ->
            if (user != null) {
                binding.toolbar.title = user.data?.name
            }
        }


        val pref = ThemePreference.getInstance(this.dataStore)
        mainViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.action_theme -> {
                    toggleNightMode()
                    true
                }
                else -> false
            }

        }

        mainViewModel.getThemeSetting().observe(this) { isNightMode ->
            // Set tema berdasarkan pengaturan yang diperbarui
            val newNightMode = if (isNightMode) {
                AppCompatDelegate.MODE_NIGHT_YES // Aktifkan mode gelap
            } else {
                AppCompatDelegate.MODE_NIGHT_NO // Matikan mode gelap
            }

            AppCompatDelegate.setDefaultNightMode(newNightMode)

            // Set ikon menu berdasarkan tema yang aktif
            val iconId = if (isNightMode) {
                R.drawable.quill_sun
            } else {
                R.drawable.quill_moon
            }
            binding.toolbar.menu.findItem(R.id.action_theme)?.icon =
                ContextCompat.getDrawable(this, iconId)

        }

    }



    private fun toggleNightMode() {
        val isNightMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        lifecycleScope.launch {
            mainViewModel.saveThemeSetting(!isNightMode)
        }

        // Set tema berdasarkan preferensi yang diperbarui
        val newNightMode = if (!isNightMode) {
            AppCompatDelegate.MODE_NIGHT_YES // Aktifkan mode gelap
        } else {
            AppCompatDelegate.MODE_NIGHT_NO // Matikan mode gelap
        }

        AppCompatDelegate.setDefaultNightMode(newNightMode)

        val iconResId = if (newNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            R.drawable.quill_moon
        } else {
            R.drawable.quill_sun
        }

        // Set ikon menu
        binding.toolbar.menu.findItem(R.id.action_theme)?.icon =
            ContextCompat.getDrawable(this, iconResId)


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