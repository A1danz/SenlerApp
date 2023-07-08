package com.itis.senlerapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.senlerapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var binding : FragmentSettingsBinding? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null;
    }
}