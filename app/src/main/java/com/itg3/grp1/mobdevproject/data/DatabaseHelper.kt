package com.itg3.grp1.mobdevproject.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.data.tables.Listings
import com.itg3.grp1.mobdevproject.data.tables.Reviews
import com.itg3.grp1.mobdevproject.data.tables.Users

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        val DATABASE_NAME = "TigerCraves.sqlite"
        val DATABASE_VERSION = 1
    }

    val users = Users(this)
    val listings = Listings(this)
    val reviews = Reviews(this)

    override fun onCreate(db: SQLiteDatabase?)
    {
        val tables = listOf(users, listings, reviews)

        for (table in tables)
        {
            table.createTable(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        val tables = listOf(users, listings, reviews)
        for (table in tables)
        {
            table.dropTable(db)
        }
        onCreate(db)
    }
}