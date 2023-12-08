package com.example.hikewise.ui.checkup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hikewise.R
import com.example.hikewise.databinding.FragmentHistoryBinding
import com.example.hikewise.databinding.FragmentQuestionHealthBinding

class QuestionHealthFragment : Fragment() {
    private lateinit var binding: FragmentQuestionHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionHealthBinding.inflate(inflater, container, false)
        return binding.root
    }
}