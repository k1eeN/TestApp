package com.example.testapp.domain.usecase

import com.example.testapp.domain.model.QuestionModel
import com.example.testapp.domain.model.TestResultModel
import kotlin.math.ceil

class CalculateResultUseCase {
    operator fun invoke(
        questionModels: List<QuestionModel>,
        selectedAnswers: Map<Int, Int>
    ): TestResultModel {
        val totalQuestions = questionModels.size
        val maxScore = questionModels.sumOf { it.score }
        val passingScore = ceil(maxScore * 0.75).toInt()
        var correctAnswers = 0
        var earnedScore = 0

        questionModels.forEach { question ->
            val selected = selectedAnswers[question.id]
            if (selected != null && selected == question.correctAnswerId) {
                correctAnswers += 1
                earnedScore += question.score
            }
        }

        return TestResultModel(
            totalQuestions = totalQuestions,
            correctAnswers = correctAnswers,
            earnedScore = earnedScore,
            maxScore = maxScore,
            passingScore = passingScore,
            passed = earnedScore >= passingScore
        )
    }
}
