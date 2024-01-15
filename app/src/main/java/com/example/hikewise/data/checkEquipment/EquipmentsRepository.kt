package com.example.hikewise.data.checkEquipment

import android.content.Context
import com.example.hikewise.data.question.QuestionRepository

class EquipmentsRepository {

    companion object {
        @Volatile
        private var instance: EquipmentsRepository? = null

        fun getInstance(): EquipmentsRepository {
            return instance ?: synchronized(this) {
                instance ?: EquipmentsRepository().also { instance = it }
            }
        }
    }

    fun getEquipments(): List<Equipment> {
        return Equipments.equipments
    }
}