package com.example.hikewise.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.DataItem
import com.example.hikewise.response.ListItem
import kotlinx.coroutines.launch

class GetMountainandArticle(private val repository: UserRepository) : ViewModel() {

    private val _mountain = MutableLiveData<List<DataItem>>()
    val mountain : LiveData<List<DataItem>> = _mountain

    private val _article = MutableLiveData<List<ListItem>>()
    val article : LiveData<List<ListItem>> = _article

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getAllMountain() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getAllMountain()
                val data = response.data.orEmpty() // Mengatasi nullable List
                _mountain.postValue(data.filterNotNull())
                _isLoading.value = false
            } catch (e: Exception) {
                // Handle error jika ada
            }
        }
    }

    fun getAllArticle() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getAllArticle()
                val data = response.data.orEmpty() // Mengatasi nullable List
                _article.postValue(data.filterNotNull())
                _isLoading.value = false
            } catch (e: Exception) {
                // Handle error jika ada
            }
        }
    }
}