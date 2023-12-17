package com.example.hikewise.ui.checkup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hikewise.R
import com.example.hikewise.adapter.EquipmentAdapter
import com.example.hikewise.databinding.ActivityResultEquipmentBinding
import com.example.hikewise.ui.equipment.DeleteAllEquipmentViewModel
import com.example.hikewise.ui.equipment.EquipmentViewModelFactory
import com.example.hikewise.ui.equipment.GetAllEquipmentViewModel

class ResultEquipmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultEquipmentBinding
    private lateinit var getViewModel: GetAllEquipmentViewModel
    private lateinit var clearViewModel : DeleteAllEquipmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultEquipmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getViewModel = ViewModelProvider(this, EquipmentViewModelFactory.getInstance(this)).get(GetAllEquipmentViewModel::class.java)
        clearViewModel = ViewModelProvider(this, EquipmentViewModelFactory.getInstance(this)).get(DeleteAllEquipmentViewModel::class.java)

        binding.btClear.setOnClickListener {
            clearViewModel.deleteAllEquipment()
            clearViewModel.deleteStatus.observe(this) {status ->
                if (status == true) {
                    val intent = Intent(this, ImageProcessActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        val adapter = EquipmentAdapter()
        binding.rvEquipment.layoutManager = LinearLayoutManager(this)
        binding.rvEquipment.adapter = adapter
        binding.rvEquipment.setHasFixedSize(true)

        getViewModel.getAllEquipment().observe(this) { data ->
            adapter.submitData(lifecycle, data)
        }

    }


}