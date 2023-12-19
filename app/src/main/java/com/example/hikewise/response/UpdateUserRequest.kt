package com.example.hikewise.response

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(

    @SerializedName("email")
    val email: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("password")
    val password: String,

    @SerializedName("identity_type")
    val identityType: String?,

    @SerializedName("identity_number")
    val identityNumber: String?,

    @SerializedName("gender")
    val gender: String?,

    @SerializedName("birthdate")
    val birthdate: String?, // Anda harus memastikan bahwa nilai ini adalah format ISO 8601

    @SerializedName("address")
    val address: String?
)
