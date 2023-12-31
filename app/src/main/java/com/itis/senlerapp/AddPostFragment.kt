package com.itis.senlerapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.GridLayoutManager
//import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.itis.senlerapp.databinding.FragmentAddPostBinding
import com.itis.senlerapp.db.DbManager
import com.itis.senlerapp.db.Posts
import com.itis.senlerapp.db.Settings
import android.content.ContentResolver
import androidx.core.net.toFile
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.util.LinkedList
import kotlin.RuntimeException

class AddPostFragment : Fragment(R.layout.fragment_add_post) {
    private var binding : FragmentAddPostBinding? = null
    private var selectedPhotos : MutableList<Uri>? = null
    private var rvAdapterPhotos : AddPhotoAdapter? = null
    private var dbManager : DbManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddPostBinding.bind(view)
        rvAdapterPhotos = AddPhotoAdapter()
        binding?.rvAddPostPhotos?.adapter = rvAdapterPhotos
        binding?.rvAddPostPhotos?.layoutManager = GridLayoutManager(requireContext(),3)

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
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
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

        rvAdapterPhotos?.updateDataset(selectedPhotos)
    }

    fun createPost() {
        val text = binding?.tietAddPostText?.text
        if (text!!.isBlank() && selectedPhotos == null) {
            Snackbar.make(requireView(), "Невозможно создать пустой пост.", Snackbar.LENGTH_SHORT).show()
            return
        }

        val vkState : Boolean? = binding?.addPostChipVk?.isChecked
        val vkGroupState : Boolean? = binding?.addPostChipVkGroup?.isChecked
        val tgState : Boolean? = binding?.addPostChipTelegram?.isChecked
        val instState : Boolean? = binding?.addPostChipInstagram?.isChecked

        val map = HashMap<String, Boolean>()

        map.put(Posts.COLUMN_NAME_VK, vkState!!)
        map.put(Posts.COLUMN_NAME_VK_GROUP, vkGroupState!!)
        map.put(Posts.COLUMN_NAME_TG, tgState!!)
        map.put(Posts.COLUMN_NAME_INST, instState!!)

        try {
            checkSettings(map)
        } catch (ex : RuntimeException) {
            Snackbar.make(this.requireView(), ex.message.toString(), Snackbar.LENGTH_SHORT)
                .show()
            return
        }

        createPostsWithApi()

        dbManager!!.open()
        dbManager!!.createPost(text.toString(), copyPhotos(selectedPhotos), map, System.currentTimeMillis())

        Snackbar.make(this.requireView(), "Пост успешно создан!", Snackbar.LENGTH_SHORT).show()
    }

    private fun createPostsWithApi() {
        return
    }

    private fun copyPhotos(photos : MutableList<Uri>?) : MutableList<Uri>? {
        if (photos == null) return photos
        val appDir = requireActivity().filesDir
        val copiedUris : MutableList<Uri> = mutableListOf()
        val appDirFile = File(appDir.absolutePath).resolveSibling("files")
        for(photo in photos) {
            copiedUris.add(copyFile(photo, appDirFile))
        }

        return copiedUris
    }

    fun copyFile(uri: Uri, destinationDirectory: File) : Uri {
        var contentResolver : ContentResolver = requireContext().contentResolver

        val inputStream = contentResolver.openInputStream(uri)
        val fileName = (uri.path?.let { File(it) })?.name

        val outputFile = File(destinationDirectory, fileName)

        inputStream.use { input ->
            FileOutputStream(outputFile).use { output ->
                val buffer = ByteArray(4 * 1024) // 4KB buffer
                var bytesRead: Int
                while (input!!.read(buffer).also { bytesRead = it } >= 0) {
                    output.write(buffer, 0, bytesRead)
                }
                output.flush()
            }
        }
        return outputFile.toUri()
    }

    private fun checkSettings(statesMap : HashMap<String, Boolean>) {
        dbManager?.open()
        val settingsMap = dbManager?.readConfig()
        settingsMap!!
        dbManager?.close()

        Log.e("TEST", statesMap[Posts.COLUMN_NAME_VK].toString())
        Log.e("TEST", settingsMap[Settings.COLUMN_NAME_VK_TOKEN]!!.isBlank().toString())
        Log.e("TEST", settingsMap[Settings.COLUMN_NAME_VK_TOKEN]!!.toString())


        // check VK checkbox
        if (statesMap[Posts.COLUMN_NAME_VK]!!) {
            if (settingsMap[Settings.COLUMN_NAME_VK_TOKEN]!!.isBlank()) throw RuntimeException(
                "Невозможно создать пост на странице ВК, т.к токен отсутствует"
            )
        }

        // check VK-group checkbox
        if (statesMap[Posts.COLUMN_NAME_VK_GROUP]!!) {
            if (settingsMap[Settings.COLUMN_NAME_VK_TOKEN]!!.isBlank()) throw RuntimeException(
                "Невозможно создать пост в группе ВК, т.к отсуствует токен"
            )
            if (settingsMap[Settings.COLUMN_NAME_VK_GROUP_ID]!!.isBlank()) throw RuntimeException(
                "Невозможно создать пост в группе ВК, т.к отсуствует id группы ВК"
            )
        }


        // check Telegram-checkbox
        if (statesMap[Posts.COLUMN_NAME_TG]!!) {
            if (settingsMap[Settings.COLUMN_NAME_VK_TOKEN]!!.isBlank()) throw RuntimeException(
                "Невозможно создать пост в Telegram, т.к отсутствует токен Telegram"
            )
            if (settingsMap[Settings.COLUMN_NAME_TG_GROUP_ID]!!.isBlank()) throw RuntimeException(
                "Невозможно создать пост в Telegram, т.к отсутствует id группы в Telegram"
            )
        }

        //check Instagram-state
        if (statesMap[Posts.COLUMN_NAME_INST]!!) {
            if (settingsMap[Settings.COLUMN_NAME_INST_TOKEN]!!.isBlank()) throw RuntimeException(
                "Невозможно создать пост в Instagram, т.к отсутсвует токен Instagram."
            )

            if (selectedPhotos == null || selectedPhotos!!.size == 0) throw RuntimeException(
                "Невозможно создать пост в Instagram, т.к вы не выбрали фото."
            )
        }


    }

    fun deletePhoto(uri: Uri) {
        val list : ArrayList<Uri> = selectedPhotos as ArrayList<Uri>
        list.remove(uri)
        selectedPhotos = list
        rvAdapterPhotos?.updateDataset(selectedPhotos)
    }

}