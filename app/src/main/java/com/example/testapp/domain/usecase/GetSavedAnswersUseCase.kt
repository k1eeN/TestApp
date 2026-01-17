package com.example.testapp.domain.usecase

import com.example.testapp.domain.repository.TestRepository

class GetSavedAnswersUseCase(
    private val repository: TestRepository
) {
    suspend operator fun invoke(): Map<Int, Int> = repository.getSavedAnswers()
}
