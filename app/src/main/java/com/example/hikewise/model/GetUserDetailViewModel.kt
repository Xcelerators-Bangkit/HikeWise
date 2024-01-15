package com.example.hikewise.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.GetUserDetailResponse
import kotlinx.coroutines.launch

class GetUserDetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userDetail = MutableLiveData<GetUserDetailResponse>()
    val userDetail : LiveData<GetUserDetailResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getUserDetail(userEmail: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = userRepository.getUserDetail(userEmail)
                _userDetail.postValue(response)
                _isLoading.value = false
            } catch (e: Exception){
                Log.d("GetUserDetailViewModel", e.message.toString())
            }
        }
    }
}