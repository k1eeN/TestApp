package com.example.testapp.domain.repository

import com.example.testapp.domain.model.QuestionModel

interface TestRepository {
    suspend fun getQuestions(): List<QuestionModel>
    suspend fun getSavedAnswers(): Map<Int, Int>
    suspend fun saveAnswer(questionId: Int, answerId: Int)
    suspend fun clearAnswers()
}
