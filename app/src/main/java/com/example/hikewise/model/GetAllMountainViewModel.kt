package com.example.hikewise.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.DataItem
import com.example.hikewise.response.GetAllMountainResponse
import kotlinx.coroutines.launch

class GetAllMountainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _mountain = MutableLiveData<List<DataItem>>()
    val mountain : LiveData<List<DataItem>> = _mountain

    fun getAllMountain() {
        viewModelScope.launch {
            try {
                val response = repository.getAllMountain()
                val data = response.data.orEmpty() // Mengatasi nullable List
                _mountain.postValue(data.filterNotNull())
            } catch (e: Exception) {
                // Handle error jika ada
                e.printStackTrace()
                Log.d("GetAllMountainViewModel", e.message.toString())
            }
        }
    }

}