package com.itis.senlerapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val recyclerView: RecyclerView = binding!!.rvPosts

        // Создаем и устанавливаем адаптер для RecyclerView
        adapter = posts?.let { PostAdapter(it) }
        recyclerView.adapter = adapter

        // Устанавливаем LayoutManager для RecyclerView (например, LinearLayoutManager)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onResume() {
        super.onResume()
        binding = view?.let { FragmentPostsBinding.bind(it) }

        if (dbManager == null) {
            dbManager = DbManager(requireContext())
        }

        dbManager?.open()
        val posts = dbManager?.getPosts()

        adapter = posts?.let { PostAdapter(it) }
        binding?.rvPosts?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        dbManager?.close()
        dbManager = null
    }
}
