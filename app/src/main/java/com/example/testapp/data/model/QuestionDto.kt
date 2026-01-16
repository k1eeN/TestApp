package com.example.testapp.data.model

import com.google.gson.annotations.SerializedName

data class QuestionDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("question")
    val question: String,
    @SerializedName("topic")
    val topic: String,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("correct_answer")
    val correctAnswer: Int,
    @SerializedName("answers")
    val answers: Map<String, String?>,
    @SerializedName("score")
    val score: Int,
)