package com.example.testapp.domain.usecase

import com.example.testapp.domain.repository.TestRepository

class SaveAnswerUseCase(
    private val repository: TestRepository
) {
    suspend operator fun invoke(questionId: Int, answerId: Int) {
        repository.saveAnswer(questionId, answerId)
    }
}
