package com.itg3.grp1.mobdevproject.DbTables

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.models.IModel
import com.itg3.grp1.mobdevproject.models.Listing

class Listings(dbHandler: SQLiteOpenHelper) : IDbTable<Listing>(dbHandler)
{
    companion object
    {
        val TBL_NAME = "Listings"
        val COL_ID = "Id"
        val COL_POSTER = "Poster"
        val COL_NAME = "Name"
        val COL_ADDRESS = "Address"
        val COL_PRICE_MIN = "PriceMin"
        val COL_PRICE_MAX = "PriceMax"
        val COL_RATING = "Rating"
        val COL_DATE_POSTED = "DatePosted"
    }

    override fun createTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_CREATE = "CREATE TABLE $TBL_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY NOT NULL, " +
                "$COL_POSTER INTEGER NOT NULL, " +
                "$COL_NAME TEXT NOT NULL, " +
                "$COL_ADDRESS TEXT NOT NULL, " +
                "$COL_PRICE_MIN REAL, " +
                "$COL_PRICE_MAX REAL, " +
                "$COL_RATING REAL, " +
                "$COL_DATE_POSTED INTEGER NOT NULL, " +
                "FOREIGN KEY ($COL_POSTER) REFERENCES ${Users.TBL_NAME}(${Users.COL_ID}) ON DELETE CASCADE" +
                ")"

        db?.execSQL(SQL_TBL_CREATE)
    }

    override fun dropTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_DROP = "DROP TABLE IF EXISTS $TBL_NAME"
        db?.execSQL(SQL_TBL_DROP)
    }

    override fun getAll(): List<Listing>
    {
        TODO("Not yet implemented")
    }

    override fun add(instance: Listing): Long
    {
        val db = dbHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_POSTER, instance.Poster.Id)
        contentValues.put(COL_NAME, instance.Name)
        contentValues.put(COL_ADDRESS, instance.Address)
        contentValues.put(COL_PRICE_MIN, instance.PriceMin)
        contentValues.put(COL_PRICE_MAX, instance.PriceMax)
        contentValues.put(COL_RATING, 0.0)
        contentValues.put(COL_DATE_POSTED, System.currentTimeMillis())

        val success = db.insert(TBL_NAME, null, contentValues)
        return success
    }

    override fun update(instance: Listing): Int
    {
        TODO("Not yet implemented")
    }

    override fun delete(instance: Listing): Int
    {
        TODO("Not yet implemented")
    }
}