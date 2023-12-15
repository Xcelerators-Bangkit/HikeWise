package com.example.hikewise.service.machinelearning

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiServiceMachine() : MachineService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://machine-learning-api-d2mhxwtmqa-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(MachineService::class.java)
        }
    }

}