package com.example.testapp.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.R
import com.example.testapp.domain.model.QuestionModel
import com.example.testapp.domain.model.TestInfoModel
import com.example.testapp.domain.model.TestResultModel
import com.example.testapp.domain.usecase.CalculateResultUseCase
import com.example.testapp.domain.usecase.ClearAnswersUseCase
import com.example.testapp.domain.usecase.GetQuestionsUseCase
import com.example.testapp.domain.usecase.GetSavedAnswersUseCase
import com.example.testapp.domain.usecase.SaveAnswerUseCase
import com.example.testapp.presentation.state.TestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val saveAnswerUseCase: SaveAnswerUseCase,
    private val getSavedAnswersUseCase: GetSavedAnswersUseCase,
    private val clearAnswersUseCase: ClearAnswersUseCase,
    private val calculateResultUseCase: CalculateResultUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<TestUiState>(TestUiState.Loading)
    val state: StateFlow<TestUiState> = _state.asStateFlow()

    private val _resultState = MutableStateFlow<TestResultModel?>(null)
    val resultState: StateFlow<TestResultModel?> = _resultState.asStateFlow()

    private var questions: List<QuestionModel> = emptyList()
    private var selectedAnswers: MutableMap<Int, Int> = mutableMapOf()

    init {
        loadTest()
    }

    private fun loadTest() {
        _state.value = TestUiState.Loading
        _resultState.value = null

        viewModelScope.launch {
            runCatching {
                val questions = getQuestionsUseCase()
                val savedAnswers = getSavedAnswersUseCase()

                this@TestViewModel.questions = questions
                selectedAnswers = savedAnswers.toMutableMap()

                val content = TestUiState.Content(
                    info = buildTestInfo(questions),
                    questionModels = questions,
                    selectedAnswers = selectedAnswers.toMap(),
                )

                content
            }.onSuccess { content ->
                _state.value = content
                _resultState.value = calculateResultUseCase(
                    content.questionModels,
                    content.selectedAnswers
                )
            }.onFailure { throwable ->
                _state.value = TestUiState.Error(
                    message = throwable.message ?: context.getString(R.string.load_error)
                )
                _resultState.value = null
            }
        }
    }

    fun onSelectAnswer(questionId: Int, answerId: Int) {
        selectedAnswers[questionId] = answerId

        viewModelScope.launch {
            saveAnswerUseCase(questionId, answerId)
        }

        updateContentState()
    }

    fun onClearTestResult() {
        viewModelScope.launch {
            clearAnswersUseCase()

            selectedAnswers.clear()

            _resultState.value = null
            updateContentState()
        }
    }

    private fun updateContentState() {
        val info = buildTestInfo(questions)
        val content = TestUiState.Content(
            info = info,
            questionModels = questions,
            selectedAnswers = selectedAnswers.toMap(),
        )

        _state.value = content
        _resultState.value = calculateResultUseCase(content.questionModels, content.selectedAnswers)
    }

    private fun buildTestInfo(questionModels: List<QuestionModel>): TestInfoModel {
        val maxScore = questionModels.sumOf { it.score }
        val passingScore = (maxScore * 75 + 99) / 100
        return TestInfoModel(
            title = context.getString(R.string.test_title_placeholder),
            description = context.getString(R.string.test_description_placeholder),
            timeLimitText = context.getString(R.string.time_limit_placeholder),
            maxScore = maxScore,
            passingScore = passingScore
        )
    }
}