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
                "${TigerCraves.User.COL_ID} INTEGER PRIMARY KEY, " +
                "${TigerCraves.User.COL_NAME_FIRST} TEXT, " +
                "${TigerCraves.User.COL_NAME_MIDDLE} TEXT, " +
                "${TigerCraves.User.COL_NAME_LAST} TEXT, " +
                "${TigerCraves.User.COL_EMAIL} TEXT, " +
                "${TigerCraves.User.COL_PASSWORD_HASH} TEXT, " +
                "${TigerCraves.User.COL_DATE_REGISTERED} INTEGER" +
                ")"

        val SQL_TBL_CREATE_LISTING = "CREATE TABLE ${TigerCraves.Listing.TABLE_NAME} (" +
                "${TigerCraves.Listing.COL_ID} INTEGER PRIMARY KEY, " +
                "${TigerCraves.Listing.COL_POSTER} INTEGER, " +
                "${TigerCraves.Listing.COL_NAME} TEXT, " +
                "${TigerCraves.Listing.COL_ADDRESS} TEXT, " +
                "${TigerCraves.Listing.COL_PRICE_MIN} REAL, " +
                "${TigerCraves.Listing.COL_PRICE_MAX} REAL, " +
                "${TigerCraves.Listing.COL_RATING} REAL, " +
                "${TigerCraves.Listing.COL_DATE_POSTED} INTEGER" +
                ")"

        val SQL_TBL_CREATE_REVIEW = "CREATE TABLE ${TigerCraves.Review.TABLE_NAME} (" +
                "${TigerCraves.Review.COL_ID} INTEGER PRIMARY KEY, " +
                "${TigerCraves.Review.COL_POSTER} INTEGER, " +
                "${TigerCraves.Review.COL_LISTING} INTEGER, " +
                "${TigerCraves.Review.COL_RATING} REAL, " +
                "${TigerCraves.Review.COL_TITLE} TEXT, " +
                "${TigerCraves.Review.COL_CONTENT} TEXT, " +
                "${TigerCraves.Review.COL_DATE_POSTED} INTEGER " +
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