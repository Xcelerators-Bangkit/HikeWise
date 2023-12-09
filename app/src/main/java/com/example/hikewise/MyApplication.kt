package com.example.hikewise

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.data.question.QuestionRepository
import com.example.hikewise.data.question.QuestionViewModel
import com.example.hikewise.data.question.QuestionViewModelFactory

class MyApplication: Application() {

    companion object {
        lateinit var questionRepository: QuestionRepository
    }

    override fun onCreate() {
        super.onCreate()

        questionRepository = QuestionRepository()
    }
}