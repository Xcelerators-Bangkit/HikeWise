package com.example.hikewise.service.machinelearning

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.di.MachineInjection
import com.example.hikewise.remote.MachineRepository

class MachineFactory(private val machineRepository: MachineRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MachineViewModel::class.java) -> {
                MachineViewModel(machineRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


    companion object {
        @Volatile
        private var instance: MachineFactory? = null

        @JvmStatic
        fun getInstance(context: Context) : MachineFactory {
            if (instance == null) {
                synchronized(MachineFactory::class.java) {
                    instance = MachineFactory(
                        MachineInjection.provideRepository(context)
                    )
                }
            }
            return instance as MachineFactory
        }
    }

}