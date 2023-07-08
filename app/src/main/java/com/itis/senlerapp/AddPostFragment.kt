package com.itis.senlerapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.itis.senlerapp.databinding.FragmentAddPostBinding

class AddPostFragment : Fragment(R.layout.fragment_add_post) {
    private var binding : FragmentAddPostBinding? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddPostBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null;
    }
}