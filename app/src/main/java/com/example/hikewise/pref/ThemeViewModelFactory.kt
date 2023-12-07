package com.example.hikewise.pref

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class ThemeViewModelFactory(private val pref : ThemePreference) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}