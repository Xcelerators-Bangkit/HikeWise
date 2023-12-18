package com.example.hikewise.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hikewise.adapter.ArticleAdapter
import com.example.hikewise.adapter.MountainAdapter
import com.example.hikewise.databinding.FragmentHomeBinding
import com.example.hikewise.model.GetAllArticleViewModel
import com.example.hikewise.model.GetAllMountainViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.ui.AllArticleActivity
import com.example.hikewise.ui.BookingActivity
import com.example.hikewise.ui.CheckUpActivity
import com.example.hikewise.ui.SearchMountainActivity


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mountain : GetAllMountainViewModel
    private lateinit var article : GetAllArticleViewModel
    private lateinit var adapter: MountainAdapter
    private lateinit var articleAdapter : ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mountain = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(GetAllMountainViewModel::class.java)
        article = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(GetAllArticleViewModel::class.java)
        adapter = MountainAdapter()
        binding.recyclerViewMountain.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMountain.adapter = adapter
        binding.recyclerViewMountain.setHasFixedSize(true)



        mountain.getAllMountain()
        mountain.mountain.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }


        articleAdapter = ArticleAdapter()
        binding.recyclerViewArticle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewArticle.adapter = articleAdapter
        binding.recyclerViewArticle.setHasFixedSize(true)

        article.getAllArticle()
        article.article.observe(viewLifecycleOwner) {
            articleAdapter.submitList(it)
        }


        binding.apply {
            card.setOnClickListener {
                val intent = Intent(requireContext(), CheckUpActivity::class.java)
                startActivity(intent)
            }
            seeArticle.setOnClickListener {
                val intent = Intent(requireContext(), AllArticleActivity::class.java)
                startActivity(intent)
            }
            seeMountain.setOnClickListener {
                val intent = Intent(requireContext(), SearchMountainActivity::class.java)
                startActivity(intent)
            }
            fabBooking.setOnClickListener {
                val intent = Intent(requireContext(), BookingActivity::class.java)
                startActivity(intent)
            }
        }
        return binding.root
    }

}