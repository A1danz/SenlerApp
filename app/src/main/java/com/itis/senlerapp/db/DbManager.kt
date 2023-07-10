package com.itis.senlerapp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.itis.senlerapp.Post
import com.itis.senlerapp.SettingsFragment
import java.io.FileInputStream

class DbManager(val context: Context) {
    val dbHelper = DbHelper(context)
    var db : SQLiteDatabase? = null

    fun open() {
        db = dbHelper.writableDatabase
    }

    fun writeConfig(tg_token : String, tg_id : String, vk_token : String, vk_id : String, inst_token : String) {
        val values = ContentValues().apply {
            put(Settings.COLUMN_NAME_TG_TOKEN, tg_token)
            put(Settings.COLUMN_NAME_TG_GROUP_ID, tg_id)
            put(Settings.COLUMN_NAME_VK_TOKEN, vk_token)
            put(Settings.COLUMN_NAME_VK_GROUP_ID, vk_id)
            put(Settings.COLUMN_NAME_INST_TOKEN, inst_token)
        }

        db?.insert(Settings.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun readConfig() : HashMap<String, String> {
        val map = HashMap<String, String>()
        map[Settings.COLUMN_NAME_TG_TOKEN] = ""
        map[Settings.COLUMN_NAME_TG_GROUP_ID] = ""
        map[Settings.COLUMN_NAME_VK_GROUP_ID] = ""
        map[Settings.COLUMN_NAME_VK_TOKEN] = ""
        map[Settings.COLUMN_NAME_INST_TOKEN] = ""


        val cursor = db?.query(Settings.TABLE_NAME, null, null, null, null, null, null)
        with(cursor) {
            while(this?.moveToNext()!!) {
                map.put(Settings.COLUMN_NAME_TG_TOKEN,
                    cursor?.getString(cursor.getColumnIndex(Settings.COLUMN_NAME_TG_TOKEN)).toString()
                )
                map.put(Settings.COLUMN_NAME_TG_GROUP_ID,
                    cursor?.getString(cursor.getColumnIndex(Settings.COLUMN_NAME_TG_GROUP_ID)).toString()
                )
                map.put(Settings.COLUMN_NAME_VK_TOKEN,
                    cursor?.getString(cursor.getColumnIndex(Settings.COLUMN_NAME_VK_TOKEN)).toString()
                )
                map.put(Settings.COLUMN_NAME_VK_GROUP_ID,
                    cursor?.getString(cursor.getColumnIndex(Settings.COLUMN_NAME_VK_GROUP_ID)).toString()
                )
                map.put(Settings.COLUMN_NAME_INST_TOKEN,
                    cursor?.getString(cursor.getColumnIndex(Settings.COLUMN_NAME_INST_TOKEN)).toString()
                )
            }
        }

        cursor?.close()
        return map
    }

    fun close() {
        dbHelper.close()
    }

    @SuppressLint("Range")
    fun createPost(text : String, photoPaths : MutableList<Uri>?, states : HashMap<String, Boolean>,
                    date : Long) {
        val values = ContentValues().apply {
            put(Posts.COLUMN_NAME_TEXT, text)
            put(Posts.COLUMN_NAME_VK, states.get(Posts.COLUMN_NAME_VK))
            put(Posts.COLUMN_NAME_VK_GROUP, states.get(Posts.COLUMN_NAME_VK_GROUP))
            put(Posts.COLUMN_NAME_TG, states.get(Posts.COLUMN_NAME_TG))
            put(Posts.COLUMN_NAME_INST, states.get(Posts.COLUMN_NAME_INST))
            put(Posts.COLUMN_NAME_DATE, date)
        }

        db?.insert(Posts.TABLE_NAME, null, values)
        val cursor = db?.query(Posts.TABLE_NAME, arrayOf(Posts.COLUMN_NAME_ID), null, null, null, null, null)
        cursor!!.moveToLast()

        val id : Int = cursor.getString(cursor.getColumnIndex(Posts.COLUMN_NAME_ID)).toString().toInt()
        cursor.close()
        insertPhotos(id, photoPaths)
    }

    fun insertPhotos(postId : Int, photoPaths: MutableList<Uri>?) {
        if (photoPaths == null) return
        for (photoPath in photoPaths) {
            var values = ContentValues().apply {
                put(Photos.COLUMN_NAME_POST_ID, postId)
                put(Photos.COLUMN_NAME_PHOTO_PATH, photoPath.toString())
            }
            db?.insert(Photos.TABLE_NAME, null, values)
        }
    }

    @SuppressLint("Range")
    fun getPosts() : ArrayList<Post> {
        val posts = ArrayList<Post>()
        val cursor = db!!.query(Posts.TABLE_NAME, null, null, null,
            null, null, null)
        with(cursor) {
            while(this?.moveToNext()!!) {
                val id = cursor.getInt(cursor.getColumnIndex(Posts.COLUMN_NAME_ID))
                val text = cursor.getString(cursor.getColumnIndex(Posts.COLUMN_NAME_TEXT))
                val date = cursor.getLong(cursor.getColumnIndex(Posts.COLUMN_NAME_DATE))
                val vkState = cursor.getInt(cursor.getColumnIndex(Posts.COLUMN_NAME_VK)) > 0
                val vkGroupState = cursor.getInt(cursor.getColumnIndex(Posts.COLUMN_NAME_VK_GROUP)) > 0
                val tgState = cursor.getInt(cursor.getColumnIndex(Posts.COLUMN_NAME_TG)) > 0
                val instState = cursor.getInt(cursor.getColumnIndex(Posts.COLUMN_NAME_INST)) > 0
                val photos = getPhotosByPostId(id)

                posts.add(Post(id, text, date, vkState, vkGroupState, tgState, instState, photos))
            }
        }
        cursor.close()
        return posts
    }

    @SuppressLint("Range")
    private fun getPhotosByPostId(id : Int) : MutableList<Uri> {
        val cursor = db!!.query(Photos.TABLE_NAME, null, "${Photos.COLUMN_NAME_POST_ID} = $id",
                                null, null, null, null)
        var list = mutableListOf<Uri>()
        with(cursor) {
            while(this?.moveToNext()!!) {
                val uri = Uri.parse(cursor.getString(cursor.getColumnIndex(Photos.COLUMN_NAME_PHOTO_PATH)))
                list.add(uri)
            }
        }
        cursor.close()
        return list
    }
}