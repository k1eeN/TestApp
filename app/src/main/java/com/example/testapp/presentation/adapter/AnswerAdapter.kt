package com.example.testapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.databinding.ItemAnswerBinding
import com.example.testapp.domain.model.AnswerOptionModel

class AnswerAdapter(
    private var items: List<AnswerOptionModel>,
    private var selectedAnswerId: Int?,
    private val onAnswerSelected: (AnswerOptionModel) -> Unit
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(items[position], items[position].id == selectedAnswerId)
    }

    override fun getItemCount(): Int = items.size

    inner class AnswerViewHolder(
        private val binding: ItemAnswerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnswerOptionModel, isSelected: Boolean) {
            binding.answerText.text = item.text

            if (isSelected) {
                binding.root.setBackgroundResource(R.drawable.bg_answer_selected_with_badge)
            } else {
                binding.root.setBackgroundResource(R.drawable.bg_answer_normal_with_badge)
            }

            binding.root.setOnClickListener {
                onAnswerSelected(item)
            }
        }
    }
}
