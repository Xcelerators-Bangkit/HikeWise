package com.example.hikewise.service

import com.example.hikewise.response.GetAllArticleResponse
import com.example.hikewise.response.GetAllMountainResponse
import com.example.hikewise.response.GetUserDetailResponse
import com.example.hikewise.response.LoginResponse
import com.example.hikewise.response.RegisterRequest
import com.example.hikewise.response.RegisterResponse
import com.google.android.gms.common.api.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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


    @GET("random/mountain")
    suspend fun getAllMountain() : GetAllMountainResponse


    @GET("all/article")
    suspend fun getAllArticle() : GetAllArticleResponse

}