package com.example.hikewise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hikewise.data.sewaalat.BookingEntity
import com.example.hikewise.databinding.ItemBookingAlatBinding

class BookingAlatAdapter :
    PagingDataAdapter<BookingEntity, BookingAlatAdapter.BookingViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding =
            ItemBookingAlatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = getItem(position)
        if (booking != null) {
            holder.bind(booking)
        }
    }

    class BookingViewHolder(val binding: ItemBookingAlatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(booking: BookingEntity) {
            // Implement logic to bind data to views in the layout
            binding.namaAlat.text = booking.alat
            binding.day.text = booking.duration.toString()
            binding.imageAlat.setImageResource(booking.image)
            binding.price.text = booking.price.toString()

            // Add more bindings as needed
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookingEntity>() {
            override fun areItemsTheSame(oldItem: BookingEntity, newItem: BookingEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BookingEntity,
                newItem: BookingEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
