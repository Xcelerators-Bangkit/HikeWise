package com.example.hikewise.ui.checkup

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hikewise.R
import com.example.hikewise.adapter.CheckEquipmentAdapter
import com.example.hikewise.data.checkEquipment.EquipmentsViewModel
import com.example.hikewise.data.checkEquipment.EquipmentsViewModelFactory
import com.example.hikewise.data.equipment.EquipmentEntity
import com.example.hikewise.databinding.ActivityEquipmentBinding
import com.example.hikewise.databinding.ActivityImageProcessBinding
import com.example.hikewise.service.machinelearning.MachineFactory
import com.example.hikewise.service.machinelearning.MachineViewModel
import com.example.hikewise.ui.equipment.EquipmentViewModelFactory
import com.example.hikewise.ui.equipment.InsertViewModel
import com.example.hikewise.utils.getImageUri
import com.example.hikewise.utils.reduceFileImage
import com.example.hikewise.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ImageProcessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageProcessBinding
    private lateinit var viewModel: MachineViewModel
    private lateinit var equipmentVieWModel : InsertViewModel
    private lateinit var equipmentsViewModel: EquipmentsViewModel

    private var currentImageUri: Uri? = null

    private var imageCapture: ImageCapture? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MachineFactory.getInstance(this)).get(MachineViewModel::class.java)
        equipmentVieWModel = ViewModelProvider(this, EquipmentViewModelFactory.getInstance(this)).get(InsertViewModel::class.java)
        equipmentsViewModel = ViewModelProvider(this, EquipmentsViewModelFactory.getInstance()).get(EquipmentsViewModel::class.java)

        equipmentsViewModel.loadEquipments()
        equipmentsViewModel.equipmentsLiveData.observe(this) { equipments ->
            if (equipments != null) {
                val adapter = CheckEquipmentAdapter()
                binding.rvCheckEquipment.layoutManager = GridLayoutManager(this, 2)
                binding.rvCheckEquipment.adapter = adapter
                adapter.submitList(equipments)
                adapter.notifyDataSetChanged()
            }
        }



        binding.btCamera.setOnClickListener {
            startCamera()
        }

        binding.btGallery.setOnClickListener {
            startGallery()
        }

        binding.cvSee.setOnClickListener {
            val intent = Intent(this, ResultEquipmentActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cvProcessPredict.setOnClickListener {
            currentImageUri?.let {uri ->
                val image = uriToFile(uri, this)
                val reduceFile = reduceFileImage(image)

                val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())

                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "file",
                    reduceFile.name,
                    requestImageFile
                )

                viewModel.isLoading.observe(this) {isLoading ->
                    binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
                }


                viewModel.predict(imageMultipart)

            }

        }

        viewModel.prediction.observe(this) { prediction ->
            val result = prediction.prediction.toString()
            if (prediction != null) {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Prediction Result")
                dialog.setMessage("Hasil prediksinya adalah $result, apakah anda mau menyimpan data equipment ini?")
                dialog.setPositiveButton("Lanjut"){ _,_ ->
                    equipmentVieWModel.insertEquipment(EquipmentEntity(
                        equipment = result
                    ))
                    equipmentVieWModel.insertStatus.observe(this) { status ->
                        if (status == true) {
                            val intent = Intent(this, ResultEquipmentActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                dialog.setNegativeButton("Batal"){ _,_ ->


                }
                val dialogView = dialog.create()
                dialogView.show()

            } else {
                Log.d("Prediction", "Prediction is null")
            }
        }



    }



    private fun startCamera(){
        currentImageUri = getImageUri(this)
        launchCamera.launch(currentImageUri)
    }

    private val launchCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){ isSucces ->
        if (isSucces){
            showImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.tvImagePredict.setImageURI(it)
        }
    }
}