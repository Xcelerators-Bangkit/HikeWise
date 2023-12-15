package com.example.hikewise.remote

import com.example.hikewise.response.RegisterRequest
import com.example.hikewise.response.RegisterResponse
import com.example.hikewise.service.ApiService

class UserRepository(private val apiService: ApiService) {

    suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        return apiService.register(registerRequest)
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}