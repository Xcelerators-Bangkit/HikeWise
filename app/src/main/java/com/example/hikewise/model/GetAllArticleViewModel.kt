package com.example.hikewise.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.remote.UserRepository
import com.example.hikewise.response.ListItem
import kotlinx.coroutines.launch

class GetAllArticleViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _article = MutableLiveData<List<ListItem>>()
    val article : LiveData<List<ListItem>> = _article

    fun getAllArticle() {
        viewModelScope.launch {
            try {
                val response = userRepository.getAllArticle()
                val data = response.data.orEmpty() // Mengatasi nullable List
                _article.postValue(data.filterNotNull())
            } catch (e: Exception) {
                // Handle error jika ada
                e.printStackTrace()
                Log.d("GetAllMountainViewModel", e.message.toString())
            }
        }
    }
}