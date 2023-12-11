package com.example.hikewise.ui.checkup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.hikewise.MyApplication
import com.example.hikewise.data.question.QuestionViewModel
import com.example.hikewise.data.question.QuestionViewModelFactory
import com.example.hikewise.databinding.ActivityResultQuestionHealthBinding
import com.example.hikewise.ui.CheckUpActivity

class ResultQuestionHealthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultQuestionHealthBinding
    private lateinit var viewModel: QuestionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultQuestionHealthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionRepository = MyApplication.questionRepository
        viewModel = ViewModelProvider(this, QuestionViewModelFactory(questionRepository))[QuestionViewModel::class.java]

        viewModel.getResultMessage()
        viewModel.message.observe(this) {message ->
            Log.d("ResultQuestionHealthActivity", "Message: $message")
            binding.tvTitle.text = message

            questionRepository.resetAnswers()
        }

        binding.btBack.setOnClickListener {
            questionRepository.resetAnswers()
            val intent = Intent(this, CheckUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}