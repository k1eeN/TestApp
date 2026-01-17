package com.example.testapp.domain.model

data class TestResultModel(
    val totalQuestions: Int,
    val correctAnswers: Int,
    val earnedScore: Int,
    val maxScore: Int,
    val passingScore: Int,
    val passed: Boolean
)
