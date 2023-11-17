package com.itg3.grp1.mobdevproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.itg3.grp1.mobdevproject.DbTables.IDbTable
import com.itg3.grp1.mobdevproject.DbTables.Listings
import com.itg3.grp1.mobdevproject.DbTables.Reviews
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
    lateinit var reviews: IDbTable

    override fun onCreate(db: SQLiteDatabase?)
    {
        users = Users()
        listings = Listings()
        reviews = Reviews()

        val tables = listOf<IDbTable>(users, listings, reviews)

        for (table in tables)
        {
            table.createTable(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        val tables = listOf<IDbTable>(users, listings, reviews)
        for (table in tables)
        {
            table.dropTable(db)
        }
        onCreate(db)
    }
}