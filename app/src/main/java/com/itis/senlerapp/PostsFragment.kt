package com.itis.senlerapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.senlerapp.databinding.FragmentPostsBinding
import com.itis.senlerapp.db.DbManager

class PostsFragment : Fragment(R.layout.fragment_posts) {
    private var binding : FragmentPostsBinding? = null
    private var dbManager : DbManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostsBinding.bind(view)

        if (dbManager == null) {
            dbManager = DbManager(view.context)
        }

        dbManager?.open()
        val posts = dbManager?.getPosts()

        Log.e("TEST", posts.toString())

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        dbManager = null
    }
}