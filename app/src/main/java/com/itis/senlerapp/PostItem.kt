package com.itis.senlerapp

import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.itis.senlerapp.databinding.ItemPostBinding

class PostItem(
    private val binding: ItemPostBinding,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(post: Post) {
        binding.run {
            tvItemPostText.text = post.text
            tvItemPostTime.text = post.date.toString()
            if (post.instState) {
                ivIpInstGroup.visibility = View.VISIBLE
            }
            if (post.tgState) {
                ivIpTg.visibility = View.VISIBLE
            }
            if (post.vkState) {
                ivIpVk.visibility = View.VISIBLE
            }
            if (post.vkGroupState) {
                ivIpVkGroup.visibility = View.VISIBLE
            }

            for (uri in post.photos) {
                val imageView = ImageView(binding.root.context)
                val tableRow = TableRow(binding.root.context)

                val layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                imageView.layoutParams = layoutParams
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP

                glide.load(uri)
                    .into(imageView)

                tableRow.addView(imageView)

                tlItemPostPhotos.addView(tableRow)
            }


        }
    }

}