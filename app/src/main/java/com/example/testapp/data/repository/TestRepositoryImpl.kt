package com.example.testapp.data.repository

import com.example.testapp.data.local.AnswerDao
import com.example.testapp.data.local.AnswerEntity
import com.example.testapp.data.mapper.toDomainModel
import com.example.testapp.data.network.TestApi
import com.example.testapp.domain.model.QuestionModel
import com.example.testapp.domain.repository.TestRepository

class TestRepositoryImpl(
    private val api: TestApi,
    private val answerDao: AnswerDao,
) : TestRepository {

    override suspend fun getQuestions(): List<QuestionModel> {
        return api.getQuestions().map { it.toDomainModel() }
    }

    override suspend fun getSavedAnswers(): Map<Int, Int> {
        return answerDao.getAll().associate { it.questionId to it.selectedAnswerId }
    }

    override suspend fun saveAnswer(questionId: Int, answerId: Int) {
        answerDao.insert(AnswerEntity(questionId = questionId, selectedAnswerId = answerId))
    }

    override suspend fun clearAnswers() {
        answerDao.clearAll()
    }
}