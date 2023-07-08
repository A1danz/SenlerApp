package com.itis.senlerapp.db

import android.provider.BaseColumns

object Settings : BaseColumns {
    const val TABLE_NAME = "settings"

    const val COLUMN_NAME_TG_TOKEN = "tg_token"
    const val COLUMN_NAME_TG_GROUP_ID = "tg_group_id"
    const val COLUMN_NAME_VK_TOKEN = "vk_token"
    const val COLUMN_NAME_VK_GROUP_ID = "vk_group_id"
    const val COLUMN_NAME_INST_TOKEN = "inst_token"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "SenlerDb.db"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_TG_TOKEN TEXT," +
                "$COLUMN_NAME_TG_GROUP_ID INTEGER," +
                "$COLUMN_NAME_VK_TOKEN TEXT," +
                "$COLUMN_NAME_VK_GROUP_ID INTEGER," +
                "$COLUMN_NAME_INST_TOKEN TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

}