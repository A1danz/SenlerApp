package com.itis.senlerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.itis.senlerapp.databinding.ItemPostBinding

class PostAdapter(
    private var list: ArrayList<Post>,
    private val glide: RequestManager,
) : RecyclerView.Adapter<PostItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PostItem = PostItem(
        binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        glide = glide,
    )

    override fun onBindViewHolder(holder: PostItem, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}