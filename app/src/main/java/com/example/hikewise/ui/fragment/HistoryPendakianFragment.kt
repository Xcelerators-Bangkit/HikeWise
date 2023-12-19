package com.example.hikewise.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hikewise.R
import com.example.hikewise.adapter.BookingPendakianAdapter
import com.example.hikewise.databinding.FragmentHistoryPendakianBinding
import com.example.hikewise.model.GetTransactionByEmailViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.pref.user.UserPreference
import com.example.hikewise.pref.user.dataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class HistoryPendakianFragment : Fragment() {

    private lateinit var binding: FragmentHistoryPendakianBinding
    private lateinit var viewModel: GetTransactionByEmailViewModel
    private lateinit var userPreference: UserPreference

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryPendakianBinding.inflate(layoutInflater)

        userPreference = UserPreference.getInstance(requireContext().dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(GetTransactionByEmailViewModel::class.java)

        val adapter = BookingPendakianAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        GlobalScope.launch {
            val emailUser = userPreference.getUserEmail.first()

            viewModel.getTransactionByEmail(
                emailUser ?: ""
            )
        }

        viewModel.getTransactionByEmail.observe(viewLifecycleOwner) { response->
            if (response != null){
                adapter.submitList(response)
            }
        }

        return binding.root
    }

}