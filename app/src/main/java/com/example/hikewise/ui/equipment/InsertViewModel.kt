package com.example.hikewise.ui.equipment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.data.equipment.EquipmentEntity
import com.example.hikewise.data.equipment.EquipmentRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val repository: EquipmentRepository) : ViewModel() {

    private val _insertStatus = MutableLiveData<Boolean>()
    val insertStatus: LiveData<Boolean> get() = _insertStatus

    fun insertEquipment(equipment: EquipmentEntity) {
        viewModelScope.launch {
            try {
                repository.insertEquipment(equipment)
                _insertStatus.postValue(true)
            } catch (e: Exception) {
                _insertStatus.postValue(false)
            }
        }
    }
}