package com.example.hikewise.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.GetTransactionResponse
import kotlinx.coroutines.launch


class GetTransactionViewModel(private val repository: UserRepository): ViewModel() {

    private val _transaction = MutableLiveData<GetTransactionResponse?>()
    val transaction : LiveData<GetTransactionResponse?> = _transaction

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getTransaction(userEmail: String, transactionId: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getTransaction(userEmail, transactionId)
                _transaction.postValue(response)
                _isLoading.value = false
            } catch (e: Exception){
                Log.d("ViewModel", e.message.toString())
            }
        }
    }
}