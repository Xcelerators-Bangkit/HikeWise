package com.example.hikewise.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.GetTransactionEmailResponse
import com.example.hikewise.response.GetTransactionEmailResponseItem
import com.example.hikewise.response.ListDataItem
import kotlinx.coroutines.launch

class GetTransactionByEmailViewModel (private val repository: UserRepository) : ViewModel() {

    private val _getTransactionByEmail = MutableLiveData<List<GetTransactionEmailResponseItem>>()
    val getTransactionByEmail : LiveData<List<GetTransactionEmailResponseItem>> = _getTransactionByEmail

    fun getTransactionByEmail(userEmail: String){
        viewModelScope.launch {
            try {
                val response = repository.getTransactionByEmail(userEmail)
                _getTransactionByEmail.value = response

            } catch (e: Exception){
                Log.d("ViewModel", e.message.toString())
            }
        }
    }
}