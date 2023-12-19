package com.example.hikewise.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hikewise.databinding.ItemBookingPendakianBinding
import com.example.hikewise.databinding.ItemHikingBinding
import com.example.hikewise.response.GetTransactionEmailResponseItem
import com.example.hikewise.response.ListDataItem
import com.example.hikewise.ui.StatusBookingPendakianActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BookingPendakianAdapter : ListAdapter<GetTransactionEmailResponseItem, BookingPendakianAdapter.BookingViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookingPendakianAdapter.BookingViewHolder {
        val binding = ItemHikingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingPendakianAdapter.BookingViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: BookingPendakianAdapter.BookingViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    class BookingViewHolder(private val binding: ItemHikingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: GetTransactionEmailResponseItem) {


            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val datefromJson = LocalDateTime.parse(item.data?.date, inputFormat)
            val formattedDate = datefromJson.format(outputFormat)


            binding.nameMountain.text = item.data?.mountainName
            binding.dateTime.text = formattedDate
            binding.priceMountain.text = item.data?.totalPrice.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, StatusBookingPendakianActivity::class.java)
                intent.putExtra("payment_detail", item.data?.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<GetTransactionEmailResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetTransactionEmailResponseItem,
            newItem: GetTransactionEmailResponseItem
        ): Boolean {
            return oldItem.data?.id == newItem.data?.id
        }

        override fun areContentsTheSame(
            oldItem: GetTransactionEmailResponseItem,
            newItem: GetTransactionEmailResponseItem
        ): Boolean {
            return oldItem == newItem
        }


    }

}