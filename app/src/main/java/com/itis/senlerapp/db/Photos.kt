package com.itis.senlerapp.db

import android.provider.BaseColumns

object Photos : BaseColumns {
    const val TABLE_NAME = "photos"

    const val COLUMN_NAME_POST_ID = "post_id"
    const val COLUMN_NAME_PHOTO_PATH = "photo_path"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "SenlerDb.db"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_POST_ID INTEGER," +
                "$COLUMN_NAME_PHOTO_PATH TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}