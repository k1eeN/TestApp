package com.example.testapp.domain.model

data class TestInfoModel(
    val title: String,
    val description: String,
    val timeLimitText: String,
    val maxScore: Int,
    val passingScore: Int
)
