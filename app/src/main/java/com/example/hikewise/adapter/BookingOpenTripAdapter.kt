package com.example.hikewise.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hikewise.data.opentrip.OpenTripEntity
import com.example.hikewise.databinding.ItemOpenTripBinding
import com.example.hikewise.ui.bookingopentrip.detailbooking.DetailOpenTripActivity

class BookingOpenTripAdapter:
    PagingDataAdapter<OpenTripEntity, BookingOpenTripAdapter.BookingOpenTripViewHolder>(DIFF_CALLBACK) {

    class BookingOpenTripViewHolder(val binding: ItemOpenTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(booking: OpenTripEntity) {
            // Implement logic to bind data to views in the layout
            binding.mountainName.text = booking.mountainName
            binding.tripName.text = booking.tripName
            binding.price.text = "${booking.price} IDR"

            // Add more bindings as needed
            itemView.setOnClickListener {
                val detailIntent = Intent(itemView.context, DetailOpenTripActivity::class.java)
                detailIntent.putExtra("detailOpenTrip", booking)
                itemView.context.startActivity(detailIntent)
            }
        }
    }

    override fun onBindViewHolder(holder: BookingOpenTripViewHolder, position: Int) {
        val booking = getItem(position)
        if (booking != null) {
            holder.bind(booking)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingOpenTripViewHolder {
        val binding =
            ItemOpenTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingOpenTripViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OpenTripEntity>() {
            override fun areItemsTheSame(oldItem: OpenTripEntity, newItem: OpenTripEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: OpenTripEntity,
                newItem: OpenTripEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}