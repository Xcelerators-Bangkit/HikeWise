package com.example.hikewise.service.machinelearning

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.MachineRepository
import com.example.hikewise.response.MachineLearningResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MachineViewModel(private val machineRepository: MachineRepository) : ViewModel() {

    private val _prediction = MutableLiveData<MachineLearningResponse>()
    val prediction: LiveData<MachineLearningResponse> = _prediction

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun predict(file: MultipartBody.Part) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = machineRepository.predict(file)
                _prediction.postValue(response)
                _isLoading.value = false
            } catch (e: Exception) {
                Log.d("ViewModel", e.message.toString())
            }
        }
    }
}