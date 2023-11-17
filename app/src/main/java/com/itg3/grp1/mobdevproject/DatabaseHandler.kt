package com.itg3.grp1.mobdevproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.DbTables.*
import com.itg3.grp1.mobdevproject.models.*

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        val DATABASE_NAME = "TigerCraves.sqlite"
        val DATABASE_VERSION = 1
    }

    lateinit var users: IDbTable<User>
    lateinit var listings: IDbTable<Listing>
    lateinit var reviews: IDbTable<Review>

    override fun onCreate(db: SQLiteDatabase?)
    {
        users = Users(this)
        listings = Listings(this)
        reviews = Reviews(this)

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