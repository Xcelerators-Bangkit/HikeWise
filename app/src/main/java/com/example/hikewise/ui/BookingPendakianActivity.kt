package com.example.hikewise.ui

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityBookingPendakianBinding
import com.example.hikewise.model.GetAllMountainViewModel
import com.example.hikewise.model.TransactionViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.pref.dataStore
import com.example.hikewise.pref.user.UserPreference
import com.example.hikewise.response.TransactionRequest
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class BookingPendakianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingPendakianBinding
    private lateinit var viewModel: GetAllMountainViewModel
    private lateinit var transactionViewModel : TransactionViewModel
    private lateinit var userPreference: UserPreference
    private lateinit var dateEditText: TextInputEditText
    private val calendar: Calendar = Calendar.getInstance()

    private var selectedMountainId: Int? = null
    private var selectDate: String? = null

    private val idToMountainNameMap: MutableMap<Int, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingPendakianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference.getInstance(this.dataStore)
        transactionViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(TransactionViewModel::class.java)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(GetAllMountainViewModel::class.java)

        dateEditText = binding.dateEditText

        dateEditText.setOnClickListener {
            showDatePicker()
        }




        viewModel.getAllMountain()
        viewModel.mountain.observe(this) {mountains ->
            if (mountains != null) {

                mountains.forEach { mountain ->
                    val mountainId = mountain.id ?: -1
                    val mountainName = mountain.name ?: ""
                    idToMountainNameMap[mountainId] = mountainName
                }

                val mountainNames: List<String> = mountains.map { it.name.orEmpty() }

                val adapter = ArrayAdapter(
                    this,
                    R.layout.list_item_mountain,
                    mountainNames
                )

                binding.mountainAutoCompleteTextView.setAdapter(adapter)

                binding.mountainAutoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                    val selectedMountain = mountains[position]
                    selectedMountainId = selectedMountain.id ?: -1

                    val selectedMountainName = idToMountainNameMap[selectedMountainId].orEmpty()

                    Log.d("MountainId" , "$selectedMountainId, $selectedMountainName")
                }
            }


        }

        GlobalScope.launch {
            val emailUser = userPreference.getUserEmail.first()

            binding.btBookPendakian.setOnClickListener {
                transactionViewModel.transaction(
                    TransactionRequest(
                        selectedMountainId ?: -1,
                        emailUser ?: "",
                        selectDate ?: ""

                    )
                )
            }


        }

        transactionViewModel.transaction.observe(this) { transaction ->
            if (transaction != null) {
                val intent = Intent(this, DetailBookingPendakianActivity::class.java)
                intent.putExtra("transaction_id", transaction.data?.id)
                startActivity(intent)
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
                setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            })

        datePicker.show(supportFragmentManager, datePicker.toString())
    }

    private fun setDate(year: Int, month: Int, day: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        calendar.set(year, month, day)
        selectDate = dateFormat.format(calendar.time)

        // Set tanggal yang dipilih ke dateEditText
        dateEditText.setText(selectDate)
    }
}