package com.example.hikewise.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hikewise.databinding.ItemMountainBinding
import com.example.hikewise.response.DataItem
import com.example.hikewise.ui.DetailMountainActivity

class MountainAdapter : ListAdapter<DataItem, MountainAdapter.MountainViewHolder>(MountainDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainViewHolder {
        val binding = ItemMountainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MountainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MountainViewHolder, position: Int) {
        val mountain = getItem(position)
        holder.bind(mountain)
    }

    class MountainViewHolder(private val binding: ItemMountainBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(mountain: DataItem) {
            Glide.with(binding.root)
                .load(mountain.imageUrl)
                .into(binding.imvMountain)
            binding.tvTitleMountain.text = mountain.name

            itemView.setOnClickListener {

                val intent = Intent(itemView.context, DetailMountainActivity::class.java)
                intent.putExtra("mountain", mountain)
                val optionCompat : ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imvMountain, "image"),
                        Pair(binding.tvTitleMountain, "title")
                    )
                itemView.context.startActivity(intent, optionCompat.toBundle())

            }
        }
    }

    private class MountainDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}
