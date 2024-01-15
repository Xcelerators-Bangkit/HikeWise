package com.example.hikewise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hikewise.R
import com.example.hikewise.data.checkEquipment.Equipment

class CheckEquipmentAdapter: ListAdapter<Equipment, CheckEquipmentAdapter.ViewHolder>(CheckEquipmentDiffCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckEquipmentAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_check_equipment, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CheckEquipmentAdapter.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.tvName.text = currentItem.name
    }

    class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.title_check_equipment)

    }

    private class CheckEquipmentDiffCallback : DiffUtil.ItemCallback<Equipment>() {
        override fun areItemsTheSame(oldItem: Equipment, newItem: Equipment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Equipment, newItem: Equipment): Boolean {
            return oldItem == newItem
        }

    }
}