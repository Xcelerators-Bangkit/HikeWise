package com.example.hikewise.ui.checkup

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hikewise.MyApplication
import com.example.hikewise.adapter.QuestionListAdapter
import com.example.hikewise.data.question.QuestionViewModel
import com.example.hikewise.data.question.QuestionViewModelFactory

import com.example.hikewise.databinding.ActivityQuestionHealthBinding

class QuestionHealthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionHealthBinding
    private lateinit var viewModel: QuestionViewModel
    private lateinit var questionAdapter: QuestionListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionHealthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionRepository = MyApplication.questionRepository
        viewModel = ViewModelProvider(this, QuestionViewModelFactory(questionRepository))[QuestionViewModel::class.java]

        questionAdapter = QuestionListAdapter { option ->
            viewModel.saveAnswer(option)
        }

        val recyclerView = binding.rvQuestion
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = questionAdapter

        viewModel.loadQuestions()
        viewModel.questionsLiveData.observe(this) { questions ->
            if ( questions != null) {
                questionAdapter.submitList(questions)
            } else {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Error")
                dialog.setMessage("Failed to load questions")
                dialog.setPositiveButton("OK") { _, _ ->

                }
                dialog.show()
            }
        }

        binding.btProsess.setOnClickListener {
            val intent = Intent(this, ResultQuestionHealthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}