package com.example.testapp.data.mapper

import com.example.testapp.data.model.QuestionDto
import com.example.testapp.domain.model.AnswerOptionModel
import com.example.testapp.domain.model.QuestionModel

internal fun QuestionDto.toDomainModel(): QuestionModel {
    val answerList = answers.entries
        .filter { it.value != null }
        .sortedBy { it.key.toIntOrNull() ?: Int.MAX_VALUE }
        .map { entry ->
            AnswerOptionModel(id = entry.key.toInt(), text = entry.value.orEmpty().trim())
        }
    return QuestionModel(
        id = id,
        topic = topic,
        text = question,
        answers = answerList,
        correctAnswerId = correctAnswer,
        score = score
    )
}