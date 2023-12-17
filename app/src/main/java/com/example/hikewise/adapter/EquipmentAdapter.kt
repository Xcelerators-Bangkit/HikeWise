package com.example.hikewise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hikewise.data.equipment.EquipmentEntity
import com.example.hikewise.databinding.ItemEquipmentBinding

class EquipmentAdapter: PagingDataAdapter<EquipmentEntity, EquipmentAdapter.EquipmentViewHolder>(EquipmentDiffCallback) {
    override fun onBindViewHolder(holder: EquipmentAdapter.EquipmentViewHolder, position: Int) {
        val equipment = getItem(position)
        if (equipment != null) {
            holder.bind(equipment)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EquipmentAdapter.EquipmentViewHolder {
        val binding = ItemEquipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EquipmentViewHolder(binding)
    }

    class EquipmentViewHolder(val binding: ItemEquipmentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(equipment: EquipmentEntity) {
            binding.tvEquipment.text = equipment.equipment
        }
    }

    companion object {
        val EquipmentDiffCallback = object : DiffUtil.ItemCallback<EquipmentEntity>() {
            override fun areItemsTheSame(
                oldItem: EquipmentEntity,
                newItem: EquipmentEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: EquipmentEntity,
                newItem: EquipmentEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}