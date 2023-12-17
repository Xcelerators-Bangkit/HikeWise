package com.example.hikewise.response

import com.google.gson.annotations.SerializedName

data class MachineLearningResponse(

	@field:SerializedName("prediction")
	val prediction: String? = null
)
