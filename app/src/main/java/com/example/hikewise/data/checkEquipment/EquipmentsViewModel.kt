package com.example.hikewise.data.checkEquipment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EquipmentsViewModel(private val equipmentsRepository: EquipmentsRepository) : ViewModel(){

    private val _equipmentsLiveData = MutableLiveData<List<Equipment>>()
    val equipmentsLiveData: LiveData<List<Equipment>> get() = _equipmentsLiveData

    fun loadEquipments() {
        val equipments = equipmentsRepository.getEquipments()
        _equipmentsLiveData.postValue(equipments)
    }
}