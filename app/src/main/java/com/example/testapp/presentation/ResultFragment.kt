package com.example.testapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.core.observeWithLifecycle
import com.example.testapp.databinding.FragmentResultBinding
import com.example.testapp.domain.model.TestResultModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var binding: FragmentResultBinding? = null
    private val viewModel: TestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentBinding = FragmentResultBinding.inflate(inflater, container, false)
        binding = currentBinding
        return currentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentBinding = binding ?: return

        viewModel.resultState.observeWithLifecycle(viewLifecycleOwner) { result ->
            if (result == null) return@observeWithLifecycle
            handleResult(currentBinding, result)
        }

        currentBinding.ivBack.setOnClickListener {
            viewModel.onClearTestResult()
            findNavController().popBackStack(R.id.testCardFragment, false)
        }
    }

    private fun handleResult(binding: FragmentResultBinding, result: TestResultModel) {
        binding.resultStatus.text = if (result.passed) {
            getString(R.string.done)
        } else {
            getString(R.string.not_done)
        }

        binding.scoreText.text = result.earnedScore.toString()
        binding.maxScoreText.text = result.maxScore.toString()

        binding.correctText.text = getString(
            R.string.correct_answers_format,
            result.correctAnswers,
            getString(R.string.from),
            result.totalQuestions
        )

        binding.tvReward1.text = getString(R.string.max_score_format, result.maxScore)
        binding.tvReward2.text = getString(R.string.passing_score_format, result.passingScore)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
