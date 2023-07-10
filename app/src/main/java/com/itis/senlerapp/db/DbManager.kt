package com.itis.senlerapp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
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
    fun createPost(text : String, photoPaths : MutableList<Uri>?, states : HashMap<String, Boolean>) {
        val values = ContentValues().apply {
            put(Posts.COLUMN_NAME_TEXT, text)
            put(Posts.COLUMN_NAME_VK, states.get(Posts.COLUMN_NAME_VK))
            put(Posts.COLUMN_NAME_VK_GROUP, states.get(Posts.COLUMN_NAME_VK_GROUP))
            put(Posts.COLUMN_NAME_TG, states.get(Posts.COLUMN_NAME_TG))
            put(Posts.COLUMN_NAME_INST, states.get(Posts.COLUMN_NAME_INST))
        }

        db?.insert(Posts.TABLE_NAME, null, values)
        val cursor = db?.query(Posts.TABLE_NAME, arrayOf(Posts.COLUMN_NAME_ID), null, null, null, null, null)
        cursor!!.moveToLast()

        val id : Int = cursor.getString(cursor.getColumnIndex(Posts.COLUMN_NAME_ID)).toString().toInt()
        cursor.close()
        insertPhotos(id, photoPaths)
    }

    private fun insertPhotos(postId : Int, photoPaths: MutableList<Uri>?) {
        if (photoPaths == null) return
        for (photoPath in photoPaths) {
            var values = ContentValues().apply {
                put(Photos.COLUMN_NAME_POST_ID, postId)
                put(Photos.COLUMN_NAME_PHOTO_PATH, photoPath.toString())
            }
            db?.insert(Photos.TABLE_NAME, null, values)
        }
    }
}