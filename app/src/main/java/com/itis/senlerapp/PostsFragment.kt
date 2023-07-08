package com.itis.senlerapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.senlerapp.databinding.FragmentPostsBinding

class PostsFragment : Fragment(R.layout.fragment_posts) {
    private var binding : FragmentPostsBinding? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostsBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null;
    }
}