package com.example.hikewise.remote

import com.example.hikewise.response.MachineLearningResponse
import com.example.hikewise.service.machinelearning.MachineService
import okhttp3.MultipartBody

class MachineRepository(private val machineService: MachineService) {

    suspend fun predict(file: MultipartBody.Part): MachineLearningResponse {
        return machineService.predict(file)
    }

    companion object {
        @Volatile
        private var instance: MachineRepository? = null

        fun getInstance(machineService: MachineService): MachineRepository =
            instance ?: synchronized(this) {
                instance ?: MachineRepository(machineService)
            }.also { instance = it }
    }

}