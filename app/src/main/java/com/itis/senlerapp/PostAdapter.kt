package com.itis.senlerapp

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTextView: TextView = itemView.findViewById(R.id.tv_item_post_text)
        private val vkImageView: ImageView = itemView.findViewById(R.id.iv_ip_vk)
        private val vkGroupImageView: ImageView = itemView.findViewById(R.id.iv_ip_vk_group)
        private val tgImageView: ImageView = itemView.findViewById(R.id.iv_ip_tg)
        private val instGroupImageView: ImageView = itemView.findViewById(R.id.iv_ip_inst_group)
        private val timeTextView: TextView = itemView.findViewById(R.id.tv_item_post_time)
        private val photosTableLayout: LinearLayout = itemView.findViewById(R.id.ll_item_post_photos)

        @SuppressLint("SimpleDateFormat")
        fun bind(post: Post) {
            textTextView.text = post.text
            vkImageView.visibility = if (post.vkState) View.VISIBLE else View.GONE
            vkGroupImageView.visibility = if (post.vkGroupState) View.VISIBLE else View.GONE
            tgImageView.visibility = if (post.tgState) View.VISIBLE else View.GONE
            instGroupImageView.visibility = if (post.instState) View.VISIBLE else View.GONE
            timeTextView.text = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Date(post.date))
            setupPhotos(post.photos)
        }

        private fun setupPhotos(photos: MutableList<Uri>) {
            // Очищаем все предыдущие изображения в LinearLayout
            photosTableLayout.removeAllViews()

            // Добавляем ImageView для каждого фото в LinearLayout
            for (photoUri in photos) {
                val imageView = ImageView(itemView.context)
                imageView.setImageURI(photoUri)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                val layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                imageView.layoutParams = layoutParams
                photosTableLayout.addView(imageView)
            }
        }
    }
}
