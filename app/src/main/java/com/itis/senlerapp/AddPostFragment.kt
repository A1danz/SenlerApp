package com.itis.senlerapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.itis.senlerapp.databinding.FragmentAddPostBinding
import com.itis.senlerapp.db.DbManager
import com.itis.senlerapp.db.Posts
import java.net.URI

class AddPostFragment : Fragment(R.layout.fragment_add_post) {
    private var binding : FragmentAddPostBinding? = null
    private var selectedPhotos : MutableList<Uri>? = null
    private var dbManager : DbManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddPostBinding.bind(view)

        if (dbManager == null) {
            dbManager = DbManager(view.context)
        }

        binding?.btnAddPhotos?.setOnClickListener {
            openGallery()
        }
        binding?.btnToPost?.setOnClickListener {
            createPost()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        dbManager = null
        selectedPhotos = null
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            selectedPhotos = mutableListOf<Uri>()

            if (data?.clipData != null) {
                val clipData = data.clipData
                for (i in 0 until clipData!!.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    selectedPhotos!!.add(uri)
                    Log.e("NAME", uri.toString())
                }
            } else if (data?.data != null) {
                val uri = data.data
                selectedPhotos!!.add(uri!!)
            }
        }
    }

    fun createPost() {
        val text = binding?.tietAddPostText?.text
        if (text!!.isBlank() && selectedPhotos == null) {
            Snackbar.make(requireView(), "Can't create empty post", Snackbar.LENGTH_SHORT).show()
            return
        }

        val vkState : Boolean? = binding?.addPostChipVk?.isCheckable
        val vkGroupState : Boolean? = binding?.addPostChipVkGroup?.isCheckable
        val tgState : Boolean? = binding?.addPostChipTelegram?.isCheckable
        val instState : Boolean? = binding?.addPostChipInstagram?.isCheckable

        val map = HashMap<String, Boolean>()

        map.put(Posts.COLUMN_NAME_VK, vkState!!)
        map.put(Posts.COLUMN_NAME_VK_GROUP, vkGroupState!!)
        map.put(Posts.COLUMN_NAME_TG, tgState!!)
        map.put(Posts.COLUMN_NAME_INST, instState!!)

        createPostsWithApi()

        dbManager!!.open()
        dbManager!!.createPost(text.toString(), selectedPhotos, map)




    }

    private fun createPostsWithApi() {
        TODO("Not yet implemented")
    }


}