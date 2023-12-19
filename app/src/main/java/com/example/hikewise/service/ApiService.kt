package com.example.hikewise.service

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
import com.google.android.gms.common.api.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("user")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse

    @POST("login")
    suspend fun login(
        @Body loginResponse: LoginResponse
    ) : retrofit2.Response<Unit>

    @GET("user/{user_email}")
    suspend fun getUserDetail(
        @Path("user_email") userEmail: String
    ) : GetUserDetailResponse

    @PUT("user")
    suspend fun updateUser(
        @Body updateRequest: UpdateUserRequest
    ) : retrofit2.Response<Unit>

    @POST("transaction")
    suspend fun transaction(
        @Body transactionRequest: TransactionRequest
    ) : TransactionResponse

    @GET("transaction/{user_email}/{transactionId}")
    suspend fun getTransaction(
        @Path("user_email") userEmail: String,
        @Path("transactionId") transactionId: String
    ) : GetTransactionResponse

    @GET("transaction/{user_email}")
    suspend fun getTransactionByEmail(
        @Path("user_email") userEmail: String
    ) : List<GetTransactionEmailResponseItem>


    @GET("random/mountain")
    suspend fun getAllMountain() : GetAllMountainResponse


    @GET("all/article")
    suspend fun getAllArticle() : GetAllArticleResponse

}