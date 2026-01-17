package com.example.testapp.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.core.observeWithLifecycle
import com.example.testapp.databinding.FragmentTestBinding
import com.example.testapp.databinding.ViewUnansweredToastBinding
import com.example.testapp.presentation.adapter.QuestionAdapter
import com.example.testapp.presentation.state.TestUiState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : Fragment() {

    private var binding: FragmentTestBinding? = null
    private val viewModel: TestViewModel by activityViewModels()
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentBinding = FragmentTestBinding.inflate(inflater, container, false)
        binding = currentBinding
        return currentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionAdapter = QuestionAdapter(emptyList(), emptyMap()) { question, answerId ->
            viewModel.onSelectAnswer(question.id, answerId)
        }
        val currentBinding = binding ?: return
        currentBinding.questionsRecycler.layoutManager = LinearLayoutManager(requireContext())
        currentBinding.questionsRecycler.adapter = questionAdapter

        currentBinding.finishButton.setOnClickListener {
            val state = viewModel.state.value
            val content = state as? TestUiState.Content ?: return@setOnClickListener

            val unansweredCount = content.questionModels.count { question ->
                content.selectedAnswers[question.id] == null
            }

            if (unansweredCount > 0) {
                showSnackBar(unansweredCount)
            } else {
                findNavController().navigate(R.id.action_testFragment_to_resultFragment)
            }
        }

        viewModel.state.observeWithLifecycle(viewLifecycleOwner) { state ->
            handleState(state)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun showSnackBar(count: Int) {
        val currentBinding = binding ?: return
        val snackbar = Snackbar.make(currentBinding.root, "", Snackbar.LENGTH_SHORT)
        snackbar.anchorView = currentBinding.finishButton
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        snackbarLayout.setBackgroundColor(Color.TRANSPARENT)
        val bottomOffset = resources.getDimensionPixelSize(R.dimen.spacing_16)
        snackbarLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin += bottomOffset
        }
        snackbarLayout.removeAllViews()
        val toastBinding = ViewUnansweredToastBinding.inflate(layoutInflater, snackbarLayout, false)
        toastBinding.unansweredText.text =
            getString(R.string.unanswered_questions_toast, count)
        snackbarLayout.addView(toastBinding.root)
        snackbar.show()
    }

    private fun handleState(state: TestUiState) {
        val currentBinding = binding ?: return
        val isContent = state is TestUiState.Content
        currentBinding.progressBar.isVisible = state is TestUiState.Loading
        currentBinding.topBar.isVisible = isContent
        currentBinding.questionsRecycler.isVisible = isContent
        currentBinding.finishButton.isVisible = isContent
        currentBinding.headerText.isVisible = isContent
        currentBinding.timeRow.isVisible = isContent

        when (state) {
            is TestUiState.Content -> {
                questionAdapter.updateData(
                    newQuestionModels = state.questionModels,
                    newSelected = state.selectedAnswers,
                )
            }
            is TestUiState.Error -> {
                currentBinding.errorText.text = state.message
            }
            else -> Unit
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
