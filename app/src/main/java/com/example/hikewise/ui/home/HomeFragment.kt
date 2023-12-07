package com.example.hikewise.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hikewise.R
import com.example.hikewise.databinding.FragmentHomeBinding
import com.example.hikewise.ui.AllArticleActivity
import com.example.hikewise.ui.BookingActivity
import com.example.hikewise.ui.CheckUpActivity
import com.example.hikewise.ui.DetailArticleActivity
import com.example.hikewise.ui.DetailMountainActivity
import com.example.hikewise.ui.SearchMountainActivity


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

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