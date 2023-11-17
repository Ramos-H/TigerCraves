package com.itg3.grp1.mobdevproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        val DATABASE_NAME = "TigerCraves.sqlite"
        val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        val SQL_TBL_USER_CREATE = "CREATE TABLE ${TigerCraves.User.TABLE_NAME} (" +
                "${TigerCraves.User.COL_ID} INTEGER PRIMARY KEY, " +
                "${TigerCraves.User.COL_NAME_FIRST} TEXT, " +
                "${TigerCraves.User.COL_NAME_MIDDLE} TEXT, " +
                "${TigerCraves.User.COL_NAME_LAST} TEXT, " +
                "${TigerCraves.User.COL_EMAIL} TEXT, " +
                "${TigerCraves.User.COL_PASSWORD_HASH} TEXT, " +
                "${TigerCraves.User.COL_DATE_REGISTERED} INTEGER" +
                ")"

        Log.d("SQL_Stuff", SQL_TBL_USER_CREATE)

        db?.execSQL(SQL_TBL_USER_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        val SQL_TBL_USER_DROP = "DROP TABLE IF EXISTS ${TigerCraves.User.TABLE_NAME}"
        db?.execSQL(SQL_TBL_USER_DROP)
        onCreate(db)
    }
}