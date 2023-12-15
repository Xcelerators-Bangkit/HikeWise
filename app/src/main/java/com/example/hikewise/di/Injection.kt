package com.example.hikewise.di

import android.content.Context
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.service.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}