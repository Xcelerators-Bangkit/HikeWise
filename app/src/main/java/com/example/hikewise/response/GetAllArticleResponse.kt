package com.example.hikewise.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetAllArticleResponse(

	@field:SerializedName("data")
	val data: List<ListItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class ListItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("source_url")
	val sourceUrl: String? = null,

	@field:SerializedName("content")
	val content: String? = null
) : Parcelable
