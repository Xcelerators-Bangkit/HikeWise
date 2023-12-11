package com.example.hikewise.ui.weather

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hikewise.R
import com.example.hikewise.databinding.FragmentWeatherBinding
import com.example.hikewise.ui.CheckUpActivity


class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)

        binding.apply {
            btnProcess.setOnClickListener {
                val intent = Intent(requireContext(), ResultWeatherActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }

}