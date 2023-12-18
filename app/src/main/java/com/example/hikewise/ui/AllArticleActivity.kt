package com.example.hikewise.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hikewise.adapter.AllArticleAdapter
import com.example.hikewise.databinding.ActivityAllArticleBinding
import com.example.hikewise.model.GetAllArticleViewModel
import com.example.hikewise.model.ViewModelFactory

class AllArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllArticleBinding
    private lateinit var viewModel : GetAllArticleViewModel
    private lateinit var adapter: AllArticleAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(GetAllArticleViewModel::class.java)

        viewModel.getAllArticle()
        viewModel.article.observe(this){ article ->
            adapter = AllArticleAdapter()
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)
            adapter.submitList(article)
            adapter.notifyDataSetChanged()

        }
    }
}