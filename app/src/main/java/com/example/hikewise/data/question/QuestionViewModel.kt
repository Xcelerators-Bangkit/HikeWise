package com.example.hikewise.data.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionViewModel(private val questionRepository: QuestionRepository) : ViewModel(){

    private val _questionsLiveData = MutableLiveData<List<Question>>()
    val questionsLiveData: LiveData<List<Question>> get() = _questionsLiveData

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message


    fun loadQuestions() {
        val questions = questionRepository.getQuestions()
        _questionsLiveData.postValue(questions)
    }

    fun saveAnswer(option: Option) {
        questionRepository.saveAnswer(option)
    }

    fun getResultMessage() {
        val answer = questionRepository.getResultMessage()
        Log.d("QuestionViewModel", "Answer for Question 1: $answer")
        _message.postValue(answer)
    }
}