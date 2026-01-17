package com.example.testapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.databinding.ItemQuestionBinding
import com.example.testapp.domain.model.QuestionModel

class QuestionAdapter(
    private var questionModels: List<QuestionModel>,
    private var selectedAnswers: Map<Int, Int>,
    private val onAnswerSelected: (questionModel: QuestionModel, answerId: Int) -> Unit
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    fun updateData(newQuestionModels: List<QuestionModel>, newSelected: Map<Int, Int>) {
        questionModels = newQuestionModels
        selectedAnswers = newSelected
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questionModels[position], selectedAnswers[questionModels[position].id], position)
    }

    override fun getItemCount(): Int = questionModels.size

    inner class QuestionViewHolder(private val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(questionModel: QuestionModel, selectedAnswerId: Int?, position: Int) {
            binding.questionIndex.text = (position + 1).toString()
            binding.questionScore.text = binding.root.context.getString(
                R.string.score_per_question_format,
                questionModel.score
            )
            binding.ivDone.isVisible = selectedAnswerId != null
            binding.questionDivider.isVisible = position != questionModels.lastIndex
            binding.questionTopic.text = questionModel.topic
            binding.questionText.text = questionModel.text
            val adapter = AnswerAdapter(questionModel.answers, selectedAnswerId) { answer ->
                onAnswerSelected(questionModel, answer.id)
            }
            binding.answersRecycler.layoutManager = LinearLayoutManager(binding.root.context)
            binding.answersRecycler.adapter = adapter
        }
    }
}
