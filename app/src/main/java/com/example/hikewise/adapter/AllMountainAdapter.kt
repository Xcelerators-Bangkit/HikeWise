package com.example.hikewise.adapter


import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hikewise.databinding.ItemAllMountainBinding
import com.example.hikewise.response.DataItem
import com.example.hikewise.ui.DetailMountainActivity

class AllMountainAdapter : ListAdapter<DataItem, AllMountainAdapter.AllMountainViewHolder>(MountainDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllMountainAdapter.AllMountainViewHolder {

        val binding = ItemAllMountainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllMountainAdapter.AllMountainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllMountainAdapter.AllMountainViewHolder, position: Int) {
        val mountain = getItem(position)
        holder.bind(mountain)
    }

    class AllMountainViewHolder(private val binding: ItemAllMountainBinding) :
        RecyclerView.ViewHolder(binding.root)  {


            fun bind(mountain: DataItem) {
                val status = if (mountain.openStatus == true) {
                    "Open"
                } else {
                    "Closed"
                }
                Glide.with(binding.root)
                    .load(mountain.imageUrl)
                    .into(binding.tvImageMountain)
                binding.tvTitleMountain.text = mountain.name
                binding.tvStatus.text = status
                binding.tvLocation.text = mountain.location

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMountainActivity::class.java)
                    intent.putExtra("detailMountain", mountain)
                    val optionsCompat : ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(binding.tvImageMountain, "image"),
                            Pair(binding.tvTitleMountain, "title"),
                            Pair(binding.tvStatus, "status"),
                            Pair(binding.tvLocation, "location")
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
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