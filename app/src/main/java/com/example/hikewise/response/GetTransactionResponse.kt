package com.example.hikewise.response

import com.google.gson.annotations.SerializedName

data class GetTransactionResponse(

	@field:SerializedName("data")
	val data: ListData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ListData(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("user_email")
	val userEmail: String? = null,

	@field:SerializedName("total_price")
	val totalPrice: Int? = null,

	@field:SerializedName("user_name")
	val userName: String? = null,

	@field:SerializedName("mountain_name")
	val mountainName: String? = null,

	@field:SerializedName("mountain_id")
	val mountainId: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("t_time")
	val tTime: String? = null
)
