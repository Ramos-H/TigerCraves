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
        val SQL_TBL_CREATE_USER = "CREATE TABLE ${TigerCraves.User.TABLE_NAME} (" +
                "${TigerCraves.User.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "${TigerCraves.User.COL_NAME_FIRST} TEXT NOT NULL, " +
                "${TigerCraves.User.COL_NAME_MIDDLE} TEXT, " +
                "${TigerCraves.User.COL_NAME_LAST} TEXT NOT NULL, " +
                "${TigerCraves.User.COL_EMAIL} TEXT NOT NULL, " +
                "${TigerCraves.User.COL_PASSWORD_HASH} TEXT NOT NULL, " +
                "${TigerCraves.User.COL_DATE_REGISTERED} INTEGER DEFAULT(unixepoch()) NOT NULL" +
                ")"

        val SQL_TBL_CREATE_LISTING = "CREATE TABLE ${TigerCraves.Listing.TABLE_NAME} (" +
                "${TigerCraves.Listing.COL_ID} INTEGER PRIMARY KEY NOT NULL, " +
                "${TigerCraves.Listing.COL_POSTER} INTEGER NOT NULL, " +
                "${TigerCraves.Listing.COL_NAME} TEXT NOT NULL, " +
                "${TigerCraves.Listing.COL_ADDRESS} TEXT NOT NULL, " +
                "${TigerCraves.Listing.COL_PRICE_MIN} REAL, " +
                "${TigerCraves.Listing.COL_PRICE_MAX} REAL, " +
                "${TigerCraves.Listing.COL_RATING} REAL, " +
                "${TigerCraves.Listing.COL_DATE_POSTED} INTEGER DEFAULT(unixepoch()) NOT NULL, " +
                "FOREIGN KEY (${TigerCraves.Listing.COL_POSTER}) REFERENCES ${TigerCraves.User.TABLE_NAME}(${TigerCraves.User.COL_ID}) ON DELETE CASCADE" +
                ")"

        val SQL_TBL_CREATE_REVIEW = "CREATE TABLE ${TigerCraves.Review.TABLE_NAME} (" +
                "${TigerCraves.Review.COL_ID} INTEGER PRIMARY KEY, " +
                "${TigerCraves.Review.COL_POSTER} INTEGER NOT NULL, " +
                "${TigerCraves.Review.COL_LISTING} INTEGER NOT NULL, " +
                "${TigerCraves.Review.COL_RATING} REAL, " +
                "${TigerCraves.Review.COL_TITLE} TEXT, " +
                "${TigerCraves.Review.COL_CONTENT} TEXT, " +
                "${TigerCraves.Review.COL_DATE_POSTED} INTEGER DEFAULT(unixepoch()) NOT NULL, " +
                "FOREIGN KEY (${TigerCraves.Review.COL_POSTER}) REFERENCES ${TigerCraves.User.TABLE_NAME}(${TigerCraves.User.COL_ID}) ON DELETE CASCADE, " +
                "FOREIGN KEY (${TigerCraves.Review.COL_LISTING}) REFERENCES ${TigerCraves.Listing.TABLE_NAME}(${TigerCraves.Listing.COL_ID}) ON DELETE CASCADE" +
                ")"

        Log.d("SQL_Stuff", SQL_TBL_CREATE_USER)
        Log.d("SQL_Stuff", SQL_TBL_CREATE_LISTING)
        Log.d("SQL_Stuff", SQL_TBL_CREATE_REVIEW)

        db?.execSQL(SQL_TBL_CREATE_USER)
        db?.execSQL(SQL_TBL_CREATE_LISTING)
        db?.execSQL(SQL_TBL_CREATE_REVIEW)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        val SQL_TBL_DROP_USER = "DROP TABLE IF EXISTS ${TigerCraves.User.TABLE_NAME}"
        val SQL_TBL_DROP_LISTING = "DROP TABLE IF EXISTS ${TigerCraves.Listing.TABLE_NAME}"
        val SQL_TBL_DROP_REVIEW = "DROP TABLE IF EXISTS ${TigerCraves.Review.TABLE_NAME}"

        db?.execSQL(SQL_TBL_DROP_USER)
        db?.execSQL(SQL_TBL_DROP_LISTING)
        db?.execSQL(SQL_TBL_DROP_REVIEW)

        onCreate(db)
    }
}