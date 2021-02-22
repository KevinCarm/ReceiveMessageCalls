package com.kevcs.receivemessagecalls

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class daoNumber(
    private val context: Context, private val database: SqlAdmin = SqlAdmin(context),
    private var base: SQLiteDatabase = database.writableDatabase
) {
    fun insert(number: String, message: String) {
        val query =
            "INSERT INTO numbers (number, message) VALUES ('${number}', '$message')"
        base.execSQL(query)
    }

    @SuppressLint("Recycle")
    fun getLastRegister(): Array<out String>? {
        base = database.readableDatabase
        val query = "SELECT * FROM numbers WHERE _ID = (SELECT MAX(_ID) FROM numbers)"
        val cursor = base.rawQuery(query, null)
        cursor.moveToFirst()
        return arrayOf(cursor.getString(1),cursor.getString(2))
    }
}