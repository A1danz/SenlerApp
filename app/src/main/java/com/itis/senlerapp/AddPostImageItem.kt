package com.itis.senlerapp

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.itis.senlerapp.databinding.AddPostImageItemBinding

class AddPostImageItem(
    private val binding: AddPostImageItemBinding
) : ViewHolder(binding.root) {
    fun onBind(uri: Uri) {
        binding.run {
            ivAddPostImageItem.setImageURI(uri)
        }
    }
}