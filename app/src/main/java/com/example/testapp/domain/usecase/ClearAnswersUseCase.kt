package com.example.testapp.domain.usecase

import com.example.testapp.domain.repository.TestRepository

class ClearAnswersUseCase(
    private val repository: TestRepository
) {
    suspend operator fun invoke() {
        repository.clearAnswers()
    }
}
