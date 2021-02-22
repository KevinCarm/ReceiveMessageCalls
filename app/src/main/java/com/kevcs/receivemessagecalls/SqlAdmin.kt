package com.kevcs.receivemessagecalls

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlAdmin(
    context: Context
) : SQLiteOpenHelper(context, "numbers", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE numbers (" +
                    "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "number VARCHAR(10)," +
                    "message text )"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}