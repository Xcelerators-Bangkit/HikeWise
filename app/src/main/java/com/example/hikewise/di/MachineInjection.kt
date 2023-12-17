package com.example.hikewise.di

import android.content.Context
import com.example.hikewise.remote.MachineRepository
import com.example.hikewise.service.machinelearning.ApiConfig

object MachineInjection {

    fun provideRepository(context: Context): MachineRepository {
        val apiService = ApiConfig.getApiServiceMachine()
        return MachineRepository.getInstance(apiService)

    }
}