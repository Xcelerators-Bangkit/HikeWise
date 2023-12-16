package com.example.hikewise.remote

import com.example.hikewise.response.GetAllArticleResponse
import com.example.hikewise.response.GetAllMountainResponse
import com.example.hikewise.response.GetUserDetailResponse
import com.example.hikewise.response.LoginResponse
import com.example.hikewise.response.RegisterRequest
import com.example.hikewise.response.RegisterResponse
import com.example.hikewise.service.ApiService
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        return apiService.register(registerRequest)
    }

    suspend fun login(loginResponse: LoginResponse): Response<Unit> {
        return apiService.login(loginResponse)
    }

    suspend fun getUserDetail(userEmail: String): GetUserDetailResponse {
        return apiService.getUserDetail(userEmail)
    }

    suspend fun getAllMountain() : GetAllMountainResponse {
        return apiService.getAllMountain()
    }

    suspend fun getAllArticle() : GetAllArticleResponse {
        return apiService.getAllArticle()
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