package com.example.testapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentTestCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestCardFragment : Fragment() {

    private var binding: FragmentTestCardBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentBinding = FragmentTestCardBinding.inflate(inflater, container, false)
        binding = currentBinding
        return currentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentBinding = binding ?: return
        currentBinding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_testCardFragment_to_testFragment)
        }
    }
}
