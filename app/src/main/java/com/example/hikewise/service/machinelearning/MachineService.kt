package com.example.hikewise.service.machinelearning

import com.example.hikewise.response.MachineLearningResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MachineService {

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part file: MultipartBody.Part
    ) : MachineLearningResponse
}