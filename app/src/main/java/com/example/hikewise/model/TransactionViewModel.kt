package com.example.hikewise.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.TransactionRequest
import com.example.hikewise.response.TransactionResponse
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: UserRepository) : ViewModel() {

    private val _transaction = MutableLiveData<TransactionResponse?>()
    val transaction : LiveData<TransactionResponse?> = _transaction

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun transaction(transactionRequest: TransactionRequest){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.transaction(transactionRequest)
                _transaction.postValue(response)
                _isLoading.value = false
            } catch (e: Exception){
                Log.d("ViewModel", e.message.toString())
            }
        }

    }
}