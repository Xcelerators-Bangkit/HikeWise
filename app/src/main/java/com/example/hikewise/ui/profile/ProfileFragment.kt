package com.example.hikewise.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hikewise.databinding.FragmentProfileBinding
import com.example.hikewise.model.GetUserDetailViewModel
import com.example.hikewise.model.ViewModelFactory
import com.example.hikewise.pref.ThemeViewModel
import com.example.hikewise.pref.dataStore
import com.example.hikewise.pref.user.UserPreference
import com.example.hikewise.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: GetUserDetailViewModel
    private lateinit var userPreference: UserPreference
    private val storageReference = FirebaseStorage.getInstance().reference
    private val PICK_IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        val updatedUser = FirebaseAuth.getInstance().currentUser
        val updatedPhotoUrl = updatedUser?.photoUrl?.toString()

        // Tampilkan gambar di ImageView (misalnya, binding.profileImage)
        updatedPhotoUrl?.let {
            Glide.with(this)
                .load(it)
                .into(binding.profileImage)
        }


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

                val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val datefromJson = LocalDateTime.parse(user.data?.birthdate, inputFormat)
                val formattedDate = datefromJson.format(outputFormat)

                binding.profileName.text = user.data?.name
                binding.profileEmail.text = user.data?.email
                binding.profileGender.text = user.data?.gender
                binding.profileNumber.setText(user.data?.identityNumber)
                binding.profileBirthdate.setText(formattedDate)
                binding.profileAddress.setText(user.data?.address)
            }
        }

        binding.editImage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }

        binding.tvEditProfile.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.tvLogout.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Logout")
            builder.setMessage("Apakah anda yakin ingin keluar?")
            builder.setPositiveButton("Ya") { _, _ ->
                auth.signOut()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }

        return binding.root


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

        binding.loading.visibility = View.VISIBLE
        imageReference.putFile(selectedImageUri)
            .addOnSuccessListener { taskSnapshot ->
                imageReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    updateUserProfilePhoto(downloadUrl)
                    binding.loading.visibility = View.GONE
                    // Now you can save the downloadUrl to your database or use it as needed
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
                // Handle unsuccessful upload
                // You can log the error or show a message to the user
            }
    }

    private fun updateUserProfilePhoto(photoUrl: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(photoUrl))
            .build()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Foto profil berhasil diperbarui di Firebase Authentication

                } else {
                    // Gagal memperbarui foto profil di Firebase Authentication
                }
            }
    }

}