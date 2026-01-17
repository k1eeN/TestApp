package com.example.testapp.presentation.state

import com.example.testapp.domain.model.QuestionModel
import com.example.testapp.domain.model.TestInfoModel

sealed class TestUiState {
    object Loading : TestUiState()
    data class Error(val message: String) : TestUiState()
    data class Content(
        val info: TestInfoModel,
        val questionModels: List<QuestionModel>,
        val selectedAnswers: Map<Int, Int>,
    ) : TestUiState()
}
