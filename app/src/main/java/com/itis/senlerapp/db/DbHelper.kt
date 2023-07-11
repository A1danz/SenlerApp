package com.itis.senlerapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.Contacts.Photo

class DbHelper(context: Context) : SQLiteOpenHelper(context, "SenlerDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Settings.SQL_CREATE_ENTRIES)
        db.execSQL(Posts.SQL_CREATE_ENTRIES)
        db.execSQL(Photos.SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(Settings.SQL_DELETE_ENTRIES)
        db.execSQL(Posts.SQL_DELETE_ENTRIES)
        db.execSQL(Photos.SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "SenlerDb.db"
    }

}