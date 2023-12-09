package com.example.hikewise.data.question

data class Question(
    val id : Int,
    val question : String,
    val options : List<String>,
    var selectedOption : String? = null
)

data class Option(
    val questionId : Int,
    val option : String
)
