package com.example.hikewise.ui.bookingalat.listbooking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hikewise.adapter.BookingAlatAdapter
import com.example.hikewise.databinding.FragmentHistoryBookingAlatBinding
import com.example.hikewise.ui.bookingalat.BookingViewModelFactory


class HistoryBookingAlatFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBookingAlatBinding
    private lateinit var viewModel: HistoryBookingAlatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = BookingViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory).get(HistoryBookingAlatViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBookingAlatBinding.inflate(inflater, container, false)

        getAllBookings()

        return binding.root
    }

    private fun getAllBookings() {
        val adapter = BookingAlatAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.getAllBookings().observe(viewLifecycleOwner) { pagingData->
            adapter.submitData(lifecycle, pagingData)
        }
    }


}