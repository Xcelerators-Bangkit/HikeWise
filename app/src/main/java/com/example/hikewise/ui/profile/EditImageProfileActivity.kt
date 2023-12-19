package com.example.hikewise.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hikewise.R
import com.example.hikewise.databinding.ActivityEditImageProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class EditImageProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditImageProfileBinding
    private lateinit var auth: FirebaseAuth
    private val storageReference = FirebaseStorage.getInstance().reference
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        binding.btGallery.setOnClickListener {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }

        binding.btSave.setOnClickListener {
            val intent = Intent(this, ProfileFragment::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            uploadImageToFirebaseStorage(selectedImageUri)
        }
    }


    private fun uploadImageToFirebaseStorage(selectedImageUri: Uri) {
        val imageName = UUID.randomUUID().toString()
        val imageReference = storageReference.child("images/$imageName.jpg")

        imageReference.putFile(selectedImageUri)
            .addOnSuccessListener { taskSnapshot ->
                imageReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    binding.profileImageEdit.setImageURI(selectedImageUri)
                    // Now you can save the downloadUrl to your database or use it as needed
                }
            }
            .addOnFailureListener { exception ->
                // Handle unsuccessful upload
                // You can log the error or show a message to the user
            }
    }
}