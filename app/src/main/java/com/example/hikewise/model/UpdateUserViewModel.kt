package com.example.hikewise.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.UpdateUserRequest
import kotlinx.coroutines.launch
import retrofit2.Response

class UpdateUserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _updateUser = MutableLiveData<Response<Unit>>()
    val updateUser: LiveData<Response<Unit>> = _updateUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun updateUser(updateRequest: UpdateUserRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.updateUser(updateRequest)
                _updateUser.postValue(response)
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                Log.d("ViewModel", e.message.toString())
            }
        }
    }
}