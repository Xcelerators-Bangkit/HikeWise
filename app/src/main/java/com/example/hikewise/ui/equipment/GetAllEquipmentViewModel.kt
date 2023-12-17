package com.example.hikewise.ui.equipment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.hikewise.data.equipment.EquipmentEntity
import com.example.hikewise.data.equipment.EquipmentRepository

class GetAllEquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {

    fun getAllEquipment() : LiveData<PagingData<EquipmentEntity>> {
        Log.d("GetAllEquipmentViewModel", "getAllEquipment")
        return repository.getAllEquipment()
    }
}