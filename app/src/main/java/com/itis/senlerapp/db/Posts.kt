package com.itis.senlerapp.db

import android.provider.BaseColumns

object Posts : BaseColumns {
    const val TABLE_NAME = "posts"

    const val COLUMN_NAME_TEXT = "text"
    const val COLUMN_NAME_DATE = "date"
    const val COLUMN_NAME_VK = "vk"
    const val COLUMN_NAME_VK_GROUP = "vk_group"
    const val COLUMN_NAME_TG = "tg"
    const val COLUMN_NAME_INST = "inst"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "SenlerDb.db"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_TEXT TEXT," +
                "$COLUMN_NAME_DATE BIGINT," +
                "$COLUMN_NAME_VK BOOLEAN," +
                "$COLUMN_NAME_VK_GROUP BOOLEAN," +
                "$COLUMN_NAME_TG BOOLEAN," +
                "$COLUMN_NAME_INST BOOLEAN)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

}