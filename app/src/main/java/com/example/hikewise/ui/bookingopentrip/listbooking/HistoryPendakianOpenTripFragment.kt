package com.example.hikewise.ui.bookingopentrip.listbooking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hikewise.adapter.BookingOpenTripAdapter
import com.example.hikewise.databinding.FragmentHistoryPendakianOpenTripBinding
import com.example.hikewise.ui.bookingopentrip.BookingOpenTripViewModelFactory


class HistoryPendakianOpenTripFragment : Fragment() {

    private lateinit var binding: FragmentHistoryPendakianOpenTripBinding
    private lateinit var viewModel: HistoryPendakianOpenTripViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = BookingOpenTripViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory).get(HistoryPendakianOpenTripViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryPendakianOpenTripBinding.inflate(inflater, container, false)

        getAllOpenTrips()

        return binding.root
    }

    private fun getAllOpenTrips() {
        val adapter = BookingOpenTripAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.getAllOpenTrips().observe(viewLifecycleOwner) { pagingData->
            adapter.submitData(lifecycle, pagingData)
        }
    }


}