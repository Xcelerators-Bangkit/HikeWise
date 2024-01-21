package com.example.hikewise.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hikewise.R
import com.example.hikewise.data.question.Option
import com.example.hikewise.data.question.Question
import com.example.hikewise.data.question.QuestionRepository

class QuestionListAdapter(
    private val onAnswerSelected: (Option) -> Unit
) : ListAdapter<Question, QuestionListAdapter.QuestionViewHolder>(QuestionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question_health, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = getItem(position)

        holder.questionText.text = question.question

        holder.radioGroup.setOnCheckedChangeListener(null)

        holder.radioGroup.check(
            when (question.selectedOption) {
                "Yes" -> R.id.rb_yes
                "No" -> R.id.rb_no
                else -> View.NO_ID
            }
        )

        holder.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedOption = when (checkedId) {
                R.id.rb_yes -> "Yes"
                R.id.rb_no -> "No"
                else -> null
            }

            question.selectedOption = selectedOption
            val answer = Option(question.id, selectedOption ?: "")
            onAnswerSelected.invoke(answer)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearFocusSelectedOption() {
        for (question in currentList) {
            question.selectedOption = null
        }
        notifyDataSetChanged()
    }

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.question_health)
        val radioGroup: RadioGroup = itemView.findViewById(R.id.rg_question_health)
    }

    private class QuestionDiffCallback : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }


}
