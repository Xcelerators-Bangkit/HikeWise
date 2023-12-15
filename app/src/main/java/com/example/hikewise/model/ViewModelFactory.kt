package com.example.hikewise.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.di.Injection
import com.example.hikewise.remote.UserRepository

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (instance == null) {
                synchronized(ViewModelFactory::class.java) {
                    instance = ViewModelFactory(
                        Injection.provideRepository(context)
                    )
                }
            }
            return instance as ViewModelFactory
        }
    }
}