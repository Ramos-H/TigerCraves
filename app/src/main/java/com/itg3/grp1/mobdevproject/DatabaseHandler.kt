package com.itg3.grp1.mobdevproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.itg3.grp1.mobdevproject.DbTables.IDbTable
import com.itg3.grp1.mobdevproject.DbTables.Listings
import com.itg3.grp1.mobdevproject.DbTables.Users

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        val DATABASE_NAME = "TigerCraves.sqlite"
        val DATABASE_VERSION = 1
    }

    lateinit var users: IDbTable
    lateinit var listings: IDbTable

    override fun onCreate(db: SQLiteDatabase?)
    {
        users = Users()
        users.createTable(db)

        listings = Listings()
        listings.createTable(db)

        val SQL_TBL_CREATE_REVIEW = "CREATE TABLE ${TigerCraves.Review.TABLE_NAME} (" +
                "${TigerCraves.Review.COL_ID} INTEGER PRIMARY KEY, " +
                "${TigerCraves.Review.COL_POSTER} INTEGER NOT NULL, " +
                "${TigerCraves.Review.COL_LISTING} INTEGER NOT NULL, " +
                "${TigerCraves.Review.COL_RATING} REAL, " +
                "${TigerCraves.Review.COL_TITLE} TEXT, " +
                "${TigerCraves.Review.COL_CONTENT} TEXT, " +
                "${TigerCraves.Review.COL_DATE_POSTED} INTEGER DEFAULT(unixepoch()) NOT NULL, " +
                "FOREIGN KEY (${TigerCraves.Review.COL_POSTER}) REFERENCES ${Users.TBL_NAME}(${Users.COL_ID}) ON DELETE CASCADE, " +
                "FOREIGN KEY (${TigerCraves.Review.COL_LISTING}) REFERENCES ${Listings.TBL_NAME}(${Listings.COL_ID}) ON DELETE CASCADE" +
                ")"

        Log.d("SQL_Stuff", SQL_TBL_CREATE_REVIEW)
        db?.execSQL(SQL_TBL_CREATE_REVIEW)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        val SQL_TBL_DROP_REVIEW = "DROP TABLE IF EXISTS ${TigerCraves.Review.TABLE_NAME}"

        db?.execSQL(SQL_TBL_DROP_REVIEW)

        onCreate(db)
    }
}