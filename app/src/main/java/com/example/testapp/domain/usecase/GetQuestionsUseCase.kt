package com.example.testapp.domain.usecase

import com.example.testapp.domain.model.QuestionModel
import com.example.testapp.domain.repository.TestRepository

class GetQuestionsUseCase(
    private val repository: TestRepository
) {
    suspend operator fun invoke(): List<QuestionModel> = repository.getQuestions()
}
