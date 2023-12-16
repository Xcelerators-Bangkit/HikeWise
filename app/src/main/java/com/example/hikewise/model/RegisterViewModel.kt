package com.example.hikewise.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.RegisterRequest
import com.example.hikewise.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel(){

    private val _register = MutableLiveData<RegisterResponse?>()
    val register: LiveData<RegisterResponse?> = _register

    fun register(registerRequest: RegisterRequest){
        viewModelScope.launch {
            try {
                val response = userRepository.register(registerRequest)
                _register.postValue(response)
            } catch (e: Exception){
                Log.d("ViewModel", e.message.toString())
            }
        }

    }
}