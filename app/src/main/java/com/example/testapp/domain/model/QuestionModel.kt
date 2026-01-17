package com.example.testapp.domain.model

data class QuestionModel(
    val id: Int,
    val topic: String,
    val text: String,
    val answers: List<AnswerOptionModel>,
    val correctAnswerId: Int,
    val score: Int
)
