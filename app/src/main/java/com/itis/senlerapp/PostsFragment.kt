package com.itis.senlerapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.itis.senlerapp.databinding.FragmentPostsBinding
import com.itis.senlerapp.db.DbManager

class PostsFragment : Fragment(R.layout.fragment_posts) {
    private var binding: FragmentPostsBinding? = null
    private var dbManager: DbManager? = null
    private var adapter: PostAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostsBinding.bind(view)

        if (dbManager == null) {
            dbManager = DbManager(view.context)
        }

        dbManager?.open()
        val posts = dbManager?.getPosts()

        adapter = PostAdapter(
            list = posts ?: ArrayList(),
            glide = Glide.with(this)
        )
        binding?.rvPosts?.adapter = adapter
        Log.e("TEST", posts.toString())

    }

    override fun onResume() {
        super.onResume()
        binding = view?.let { FragmentPostsBinding.bind(it) }

        if (dbManager == null) {
            dbManager = view?.let { DbManager(it.context) }
        }

        dbManager?.open()
        val posts = dbManager?.getPosts()

        adapter = PostAdapter(
            list = posts ?: ArrayList(),
            glide = Glide.with(this)
        )
        binding?.rvPosts?.adapter = adapter
        Log.e("TEST", posts.toString())
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
        dbManager = null
    }
}