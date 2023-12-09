package com.example.hikewise.data.question

import android.util.Log

class QuestionRepository {
    private val answersMap = mutableMapOf<Int, String>()

    fun getQuestions(): List<Question> {
        return QuestionsList.questions
    }

    fun saveAnswer(answer: Option) {
        answersMap[answer.questionId] = answer.option
        Log.d("Save Answer", "Answer saved for Question ${answer.questionId}: ${answer.option}")
    }

    fun resetAnswers() {
        answersMap.clear()
        Log.d("Reset Answers", "Answers reset")
    }

    private fun getAnswerByQuestion(questionId: Int): String? {
        Log.d("Get Question", "Answer for Question $questionId: ${answersMap[questionId]}")
        return answersMap[questionId]
    }

    fun getResultMessage(): String {
        val answerForQuestion1 = getAnswerByQuestion(1)
        val answerForQuestion2 = getAnswerByQuestion(2)
        val answerForQuestion3 = getAnswerByQuestion(3)
        val answerForQuestion4 = getAnswerByQuestion(4)


        return when {
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes" && answerForQuestion4 == "Yes" -> {
                "Kemungkinan anda tidak lama lagi"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes" && answerForQuestion4 == "No" -> {
                "Anda memiliki risiko ringan. Segera temui dokter."
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No" && answerForQuestion4 == "Yes" -> {
                "minimal minum paracetamol"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No" && answerForQuestion4 == "No" -> {
                "minimal makan"
            }
            else -> {
                "Tidak perlu ngapa-ngapain"
            }
        }
    }
}
