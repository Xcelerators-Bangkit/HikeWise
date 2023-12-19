package com.example.hikewise.response


import com.google.gson.annotations.SerializedName


data class TransactionResponse(

	@field:SerializedName("data")
	val data: TransactionData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class TransactionData(

	@SerializedName("id")
	val id : String? = null
)

data class TransactionRequest(
	@SerializedName("mountain_id")
	val mountainId: Int,

	@SerializedName("user_email")
	val userEmail: String,

	@SerializedName("date")
	val date: String
)
