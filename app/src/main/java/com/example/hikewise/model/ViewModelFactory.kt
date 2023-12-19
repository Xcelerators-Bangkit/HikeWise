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
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(GetUserDetailViewModel::class.java) -> {
                GetUserDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(GetAllMountainViewModel::class.java) -> {
                GetAllMountainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(GetAllArticleViewModel::class.java) -> {
                GetAllArticleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UpdateUserViewModel::class.java) -> {
                UpdateUserViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TransactionViewModel::class.java) -> {
                TransactionViewModel(repository) as T
            }
            modelClass.isAssignableFrom(GetTransactionViewModel::class.java) -> {
                GetTransactionViewModel(repository) as T
            }
            modelClass.isAssignableFrom(GetTransactionByEmailViewModel::class.java) -> {
                GetTransactionByEmailViewModel(repository) as T
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