package com.example.hikewise.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hikewise.R
import com.example.hikewise.databinding.FragmentProfileBinding
import com.example.hikewise.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        binding.btLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }


        return binding.root




    }


}