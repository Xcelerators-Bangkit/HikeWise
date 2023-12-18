package com.example.hikewise.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.databinding.FragmentProfileBinding
import com.example.hikewise.model.GetUserDetailViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.pref.ThemeViewModel
import com.example.hikewise.pref.dataStore
import com.example.hikewise.pref.user.UserPreference
import com.example.hikewise.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: GetUserDetailViewModel
    private lateinit var userPreference: UserPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        userPreference = UserPreference.getInstance(requireContext().dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(GetUserDetailViewModel::class.java)

        GlobalScope.launch {
            val emailUser = userPreference.getUserEmail.first()
            if (emailUser != null) {
                viewModel.getUserDetail(emailUser)
            }
        }

        viewModel.userDetail.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.nameEditText.setText(user.data?.name)
                binding.emailEditText.setText(user.data?.email)
                binding.addressEditText.setText(user.data?.address)
                binding.dateEditText.setText(user.data?.birthdate)
                binding.genderEditText.setText(user.data?.gender)
            }
        }

        binding.btLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root


    }


}