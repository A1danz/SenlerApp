package com.itis.senlerapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
        private val photosTableLayout: TableLayout = itemView.findViewById(R.id.tl_item_post_photos)

        fun bind(post: Post) {
            textTextView.text = post.text
            vkImageView.visibility = if (post.vkState) View.VISIBLE else View.GONE
            vkGroupImageView.visibility = if (post.vkGroupState) View.VISIBLE else View.GONE
            tgImageView.visibility = if (post.tgState) View.VISIBLE else View.GONE
            instGroupImageView.visibility = if (post.instState) View.VISIBLE else View.GONE
            timeTextView.text = post.date.toString()
            setupPhotos(post.photos)
        }

        private fun setupPhotos(photos: MutableList<Uri>) {
            // Очищаем все предыдущие изображения в TableLayout
            photosTableLayout.removeAllViews()

            // Создаем TableRow и добавляем в него ImageView для каждого фото
            val row = TableRow(itemView.context)
            for (photoUri in photos) {
                val imageView = ImageView(itemView.context)
                imageView.setImageURI(photoUri)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.layoutParams =
                    TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                row.addView(imageView)
            }

            // Добавляем TableRow в TableLayout
            photosTableLayout.addView(row)
        }
    }
}
