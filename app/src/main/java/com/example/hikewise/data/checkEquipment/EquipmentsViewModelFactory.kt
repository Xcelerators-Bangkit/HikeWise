package com.example.hikewise.data.checkEquipment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.data.equipment.EquipmentRepository
import com.example.hikewise.ui.equipment.EquipmentViewModelFactory

class EquipmentsViewModelFactory(private val equipmentsRepository: EquipmentsRepository) : ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: EquipmentsViewModelFactory? = null

        fun getInstance(): EquipmentsViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: EquipmentsViewModelFactory(
                    EquipmentsRepository.getInstance()
                )
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EquipmentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EquipmentsViewModel(equipmentsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}