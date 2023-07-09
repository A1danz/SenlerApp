package com.itis.senlerapp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
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

    fun createPost(text : String, paths : ArrayList<String>) {
        for (path in paths) {
            var fis : FileInputStream = FileInputStream(path)
        }

    }

}