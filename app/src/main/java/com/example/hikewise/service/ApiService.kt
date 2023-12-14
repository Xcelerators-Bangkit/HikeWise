package com.example.hikewise.service

import com.example.hikewise.response.LoginResponse
import com.example.hikewise.response.RegisterRequest
import com.example.hikewise.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("user")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse

    @POST("login")
    suspend fun login(
        @Body loginResponse: LoginResponse
    )
}