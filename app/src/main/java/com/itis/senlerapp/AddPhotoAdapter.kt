package com.itis.senlerapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.itis.senlerapp.databinding.AddPostImageItemBinding

class AddPhotoAdapter() : RecyclerView.Adapter<AddPostImageItem>() {
    var list : MutableList<Uri> = ArrayList<Uri>();
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int)
    : AddPostImageItem = AddPostImageItem(
            AddPostImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AddPostImageItem, position: Int) {
        holder.onBind(list[position])
    }

    fun updateDataset(newList: MutableList<Uri>?) {
        if(newList!=null){
            list = newList
            notifyDataSetChanged()
        }
    }

}