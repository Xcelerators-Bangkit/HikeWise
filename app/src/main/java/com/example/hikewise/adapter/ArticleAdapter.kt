package com.example.hikewise.adapter

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hikewise.databinding.ItemArticleBinding
import com.example.hikewise.response.ListItem
import com.example.hikewise.ui.DetailArticleActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ArticleAdapter : ListAdapter<ListItem, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleAdapter.ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleAdapter.ArticleViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ArticleAdapter.ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

            @RequiresApi(Build.VERSION_CODES.O)
            fun bind(article: ListItem) {
                val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val datefromJson = LocalDateTime.parse(article.date, inputFormat)
                val formattedDate = datefromJson.format(outputFormat)

                Glide.with(binding.root)
                    .load(article.imageUrl)
                    .into(binding.tvImage)
                binding.tvTitle.text = article.content
                binding.tvDate.text = formattedDate

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailArticleActivity::class.java)
                    intent.putExtra("article", article)
                    val optionCompat : ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(binding.tvImage, "image"),
                            Pair(binding.tvTitle, "title"),
                            Pair(binding.tvDate, "date")
                        )
                    itemView.context.startActivity(intent, optionCompat.toBundle())
                }
            }

        }

    private class ArticleDiffCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }

    }
}