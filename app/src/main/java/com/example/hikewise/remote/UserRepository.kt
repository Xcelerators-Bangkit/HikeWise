package com.example.hikewise.remote

import com.example.hikewise.response.GetAllArticleResponse
import com.example.hikewise.response.GetAllMountainResponse
import com.example.hikewise.response.GetTransactionEmailResponse
import com.example.hikewise.response.GetTransactionEmailResponseItem
import com.example.hikewise.response.GetTransactionResponse
import com.example.hikewise.response.GetUserDetailResponse
import com.example.hikewise.response.ListDataItem
import com.example.hikewise.response.LoginResponse
import com.example.hikewise.response.RegisterRequest
import com.example.hikewise.response.RegisterResponse
import com.example.hikewise.response.TransactionRequest
import com.example.hikewise.response.TransactionResponse
import com.example.hikewise.response.UpdateUserRequest
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

    suspend fun updateUser(updateRequest: UpdateUserRequest): Response<Unit> {
        return apiService.updateUser(updateRequest)
    }

    suspend fun getAllMountain() : GetAllMountainResponse {
        return apiService.getAllMountain()
    }

    suspend fun getAllArticle() : GetAllArticleResponse {
        return apiService.getAllArticle()
    }

    suspend fun transaction(transactionRequest: TransactionRequest) : TransactionResponse {
        return apiService.transaction(transactionRequest)
    }

    suspend fun getTransaction(userEmail: String, transactionId: String) : GetTransactionResponse {
        return apiService.getTransaction(userEmail, transactionId)
    }

    suspend fun getTransactionByEmail(userEmail: String) : List<GetTransactionEmailResponseItem> {
        return apiService.getTransactionByEmail(userEmail)
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