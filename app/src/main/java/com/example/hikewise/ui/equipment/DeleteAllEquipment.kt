package com.example.hikewise.ui.equipment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikewise.data.equipment.EquipmentRepository
import kotlinx.coroutines.launch

class DeleteAllEquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {

    private val _deleteStatus = MutableLiveData<Boolean>()
    val deleteStatus: LiveData<Boolean> get() = _deleteStatus
    fun deleteAllEquipment() {
        viewModelScope.launch {
            try {
                repository.deleteAllEquipment()
                _deleteStatus.postValue(true)
            } catch (e: Exception) {
                _deleteStatus.postValue(false)
            }
        }
    }
}