package com.itis.senlerapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.itis.senlerapp.databinding.FragmentInstructionBinding
import com.itis.senlerapp.databinding.FragmentPostsBinding

class InstructionFragment : Fragment(R.layout.fragment_instruction) {
    private var binding : FragmentInstructionBinding? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInstructionBinding.bind(view)

        binding?.btnBack?.setOnClickListener {
            backToSettings()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null;
    }

    fun backToSettings() {
        findNavController().navigate(R.id.action_instructionFragment_to_settingsFragment)
    }
}