package com.example.hikewise.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hikewise.R
import com.example.hikewise.data.sewaalat.AlatCamping
import com.example.hikewise.ui.bookingalat.booking.BookingAlatActivity

class AlatCampingAdapter : ListAdapter<AlatCamping, AlatCampingAdapter.AlatViewHolder>(AlatDiffCallback()) {

    class AlatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.tv_image_alat)
        val textViewName: TextView = itemView.findViewById(R.id.tv_title_alat)
        val textViewPrice: TextView = itemView.findViewById(R.id.tv_price_alat)
        val button: CardView = itemView.findViewById(R.id.bt_booking_alat_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alat_camping, parent, false)
        val holder = AlatViewHolder(itemView)

        holder.button.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val alat = getItem(position)
                val intent = Intent(itemView.context, BookingAlatActivity::class.java)
                intent.putExtra("alat", alat)
                itemView.context.startActivity(intent)
            }
        }
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AlatViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.imageView.setImageResource(currentItem.image)
        holder.textViewName.text = currentItem.name
        holder.textViewPrice.text = "Price: ${currentItem.price} IDR"

    }



    // DiffUtil Callback to calculate the difference between two lists
    private class AlatDiffCallback : DiffUtil.ItemCallback<AlatCamping>() {
        override fun areItemsTheSame(oldItem: AlatCamping, newItem: AlatCamping): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlatCamping, newItem: AlatCamping): Boolean {
            return oldItem == newItem
        }
    }
}
