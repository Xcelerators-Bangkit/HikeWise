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
import com.example.hikewise.databinding.ItemAllArticleBinding
import com.example.hikewise.databinding.ItemArticleBinding
import com.example.hikewise.response.ListItem
import com.example.hikewise.ui.DetailArticleActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AllArticleAdapter : ListAdapter<ListItem, AllArticleAdapter.AllArticleViewHolder>(AllArticleDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllArticleAdapter.AllArticleViewHolder {
        val binding = ItemAllArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllArticleAdapter.AllArticleViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AllArticleAdapter.AllArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class AllArticleViewHolder(
        val binding: ItemAllArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(article: ListItem) {

            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val datefromJson = LocalDateTime.parse(article.date, inputFormat)
            val formattedDate = datefromJson.format(outputFormat)

            Glide.with(binding.root)
                .load(article.imageUrl)
                .into(binding.tvImage)
            binding.tvSumber.text = article.sourceUrl
            binding.tvDate.text = formattedDate
            binding.tvDescArticle.text = article.content

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailArticleActivity::class.java)
                intent.putExtra("all_article", article)
                val optionsCompat : ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.tvImage, "image"),
                        Pair(binding.tvSumber, "sumber"),
                        Pair(binding.tvDate, "date"),
                        Pair(binding.tvDescArticle, "article")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }


    private class AllArticleDiffCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }

    }
}