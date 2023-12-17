package com.example.hikewise.ui.equipment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.data.equipment.EquipmentRepository

class EquipmentViewModelFactory(private val equipmentRepository: EquipmentRepository) : ViewModelProvider.Factory {


    companion object {
        @Volatile
        private var instance: EquipmentViewModelFactory? = null

        fun getInstance(context: Context): EquipmentViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: EquipmentViewModelFactory(
                    EquipmentRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(InsertViewModel::class.java) -> {
                InsertViewModel(equipmentRepository) as T
            }
            modelClass.isAssignableFrom(GetAllEquipmentViewModel::class.java)-> {
                GetAllEquipmentViewModel(equipmentRepository) as T
            }
            modelClass.isAssignableFrom(DeleteAllEquipmentViewModel::class.java)-> {
                DeleteAllEquipmentViewModel(equipmentRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}