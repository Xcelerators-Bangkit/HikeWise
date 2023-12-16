package com.example.hikewise.response

import com.google.gson.annotations.SerializedName

data class GetUserDetailResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("identity_number")
	val identityNumber: String? = null,

	@field:SerializedName("identity_type")
	val identityType: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
