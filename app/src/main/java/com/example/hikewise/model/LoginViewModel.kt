package com.example.hikewise.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _login = MutableLiveData<Response<Unit>>()
    val login: LiveData<Response<Unit>> = _login

    fun login(loginResponse: LoginResponse) {
        viewModelScope.launch {
            try {
                val response = userRepository.login(loginResponse)
                _login.postValue(response)
            } catch (e: Exception) {
                Log.d("ViewModel", e.message.toString())
            }
        }
    }
}
