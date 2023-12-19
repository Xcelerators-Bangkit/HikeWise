package com.example.hikewise.ui.profile

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityEditProfileBinding
import com.example.hikewise.model.GetUserDetailViewModel
import com.example.hikewise.model.UpdateUserViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.pref.user.UserPreference
import com.example.hikewise.pref.user.dataStore
import com.example.hikewise.response.UpdateUserRequest
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userPreference: UserPreference
    private lateinit var getViewModel: GetUserDetailViewModel
    private lateinit var updateViewModel: UpdateUserViewModel

    private val calendar: Calendar = Calendar.getInstance()

    private lateinit var identity_type: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference.getInstance(this.dataStore)
        getViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        ).get(GetUserDetailViewModel::class.java)
        updateViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        ).get(UpdateUserViewModel::class.java)

        GlobalScope.launch {
            val emailUser = userPreference.getUserEmail.first()

            if (emailUser != null) {
                getViewModel.getUserDetail(emailUser)


            }
        }

        getViewModel.userDetail.observe(this) { user ->
            if (user != null) {

                identity_type = user.data?.identityType.toString()

                val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val datefromJson = LocalDateTime.parse(user.data?.birthdate, inputFormat)
                val formattedDate = datefromJson.format(outputFormat)

                binding.edtNameEditProfile.setText(user.data?.name)
                binding.edtEmailEditProfile.setText(user.data?.email)
                binding.edtNumberEditProfile.setText(user.data?.identityNumber)
                binding.edtGenderEditProfile.setText(user.data?.gender)
                binding.edtBirthdayEditProfile.setText(formattedDate)
                binding.edtAddressEditProfile.setText(user.data?.address)
            }
        }

        binding.edtBirthdayEditProfile.setOnClickListener {
            showDatePicker()
        }

        updateViewModel.isLoading.observe(this) {
            binding.loading.visibility = if (it) {
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }
        }

        binding.btEditProcess.setOnClickListener {


            val name = binding.edtNameEditProfile.text.toString()
            val number = binding.edtNumberEditProfile.text.toString()

            val address = binding.edtAddressEditProfile.text.toString()

            GlobalScope.launch {
                val email = userPreference.getUserEmail.first()
                val password = userPreference.getUserPassword.first()

                updateViewModel.updateUser(
                    UpdateUserRequest(
                        email ?: "",
                        name,
                        password ?: "",
                        identity_type,
                        number,
                        binding.edtGenderEditProfile.text.toString(),
                        binding.edtBirthdayEditProfile.text.toString(),
                        address
                    )
                )

            }

        }

        updateViewModel.updateUser.observe(this) {result ->
            if (result.isSuccessful) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Success")
                builder.setMessage("Update User Success")
                builder.setPositiveButton("OK") { _, _ ->
                    val intent = Intent(this, ProfileFragment::class.java)
                    startActivity(intent)
                    finish()
                }
                builder.create().show()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Failed")
                builder.setMessage("Update Gagal")
                builder.setPositiveButton("OK") { _, _ ->

                }
                builder.create().show()
            }
        }


    }

    private fun showDatePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val datePicker = builder.build()

        datePicker.addOnPositiveButtonClickListener(
            MaterialPickerOnPositiveButtonClickListener<Long> { selection ->
                val selectedDate = selection ?: 0
                calendar.timeInMillis = selectedDate

                // Set tanggal yang dipilih ke dateEditText
                setDate(
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
                        Calendar.DAY_OF_MONTH
                    )
                )
            })

        datePicker.show(supportFragmentManager, datePicker.toString())
    }

    private fun setDate(year: Int, month: Int, day: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        calendar.set(year, month, day)
        val selectedDate = dateFormat.format(calendar.time)

        // Set tanggal yang dipilih ke dateEditText
        binding.edtBirthdayEditProfile.setText(selectedDate)
    }
}