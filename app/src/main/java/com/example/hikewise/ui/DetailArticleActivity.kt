package com.example.hikewise.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityDetailArticleBinding
import com.example.hikewise.response.ListItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getParcelableExtra<ListItem>("article")
        if (article != null) {
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
        }

        val all_article = intent.getParcelableExtra<ListItem>("all_article")
        if (all_article != null) {
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val datefromJson = LocalDateTime.parse(all_article.date, inputFormat)
            val formattedDate = datefromJson.format(outputFormat)
            Glide.with(binding.root)
                .load(all_article.imageUrl)
                .into(binding.tvImage)
            binding.tvSumber.text = all_article.sourceUrl
            binding.tvDate.text = formattedDate
            binding.tvDescArticle.text = all_article.content
        }
    }
}