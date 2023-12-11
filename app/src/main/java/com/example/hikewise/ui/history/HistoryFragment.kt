package com.example.hikewise.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import com.example.hikewise.R
import com.example.hikewise.adapter.SectionPagerAdapter
import com.example.hikewise.databinding.FragmentHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.hiking,
            R.string.trip,
            R.string.alat
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)



        val viewPager = binding.viewPager
        val sectionPagerAdapter = SectionPagerAdapter(requireActivity())
        viewPager.adapter = sectionPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        return binding.root
    }


}