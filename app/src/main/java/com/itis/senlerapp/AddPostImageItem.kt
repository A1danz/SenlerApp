package com.itis.senlerapp

import android.net.Uri
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.itis.senlerapp.databinding.AddPostImageItemBinding

class AddPostImageItem(
    private val binding: AddPostImageItemBinding
) : ViewHolder(binding.root) {
    fun onBind(uri: Uri) {
        binding.run {
            ivAddPostImageItem.setImageURI(uri)
            btnDelete.setOnClickListener() {
                FragmentManager.findFragment<AddPostFragment>(root).deletePhoto(uri)
            }
        }
    }
}