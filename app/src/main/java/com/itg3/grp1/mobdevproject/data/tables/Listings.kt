package com.itg3.grp1.mobdevproject.data.tables

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.itg3.grp1.mobdevproject.data.DatabaseHelper
import com.itg3.grp1.mobdevproject.data.models.Listing
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

class Listings(dbHandler: DatabaseHelper) : DbTable<Listing>(dbHandler)
{
    companion object
    {
        val TBL_NAME = "Listings"
        val COL_ID = "Id"
        val COL_NAME = "Name"
        val COL_ADDRESS = "Address"
        val COL_GMAP = "GMapLink"
        val COL_PRICE_MIN = "PriceMin"
        val COL_PRICE_MAX = "PriceMax"
        val COL_RATING = "Rating"
        val COL_DATE_POSTED = "DatePosted"
    }

    override fun createTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_CREATE = "CREATE TABLE $TBL_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY NOT NULL, " +
                "$COL_NAME TEXT NOT NULL, " +
                "$COL_ADDRESS TEXT NOT NULL," +
                "$COL_GMAP TEXT, " +
                "$COL_PRICE_MIN REAL, " +
                "$COL_PRICE_MAX REAL, " +
                "$COL_RATING REAL, " +
                "$COL_DATE_POSTED INTEGER NOT NULL " +
                ")"

        db?.execSQL(SQL_TBL_CREATE)
    }

    override fun dropTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_DROP = "DROP TABLE IF EXISTS $TBL_NAME"
        db?.execSQL(SQL_TBL_DROP)
    }

    @SuppressLint("Range")
    override fun getOne(id: Int): Listing?
    {
        val SELECT_QUERY = "SELECT * FROM $TBL_NAME WHERE $COL_ID = ?"
        val db = dbHelper.readableDatabase
        var result: Listing? = null
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(SELECT_QUERY, arrayOf(id.toString()))

            if(cursor.moveToFirst())
            {
                result = Listing(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(COL_GMAP)),
                    cursor.getDouble(cursor.getColumnIndex(COL_PRICE_MIN)),
                    cursor.getDouble(cursor.getColumnIndex(COL_PRICE_MAX)),
                    cursor.getDouble(cursor.getColumnIndex(COL_RATING)),
                    LocalDateTime.ofEpochSecond(cursor.getLong(cursor.getColumnIndex(COL_DATE_POSTED)), 0, ZoneOffset.UTC)
                )

                result.Images = dbHelper.images.getAll().filter { it.ListingId == result.Id }
            }
        }
        finally
        {
            cursor?.close()
        }
        return result
    }

    @SuppressLint("Range")
    override fun getAll(): List<Listing>
    {
        val result = ArrayList<Listing>()
        val SELECT_QUERY = "SELECT * FROM $TBL_NAME"
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(SELECT_QUERY, null)

            if(cursor.moveToFirst())
            {
                do
                {
                    val listing = Listing(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(COL_GMAP)),
                        cursor.getDouble(cursor.getColumnIndex(COL_PRICE_MIN)),
                        cursor.getDouble(cursor.getColumnIndex(COL_PRICE_MAX)),
                        cursor.getDouble(cursor.getColumnIndex(COL_RATING)),
                        LocalDateTime.ofEpochSecond(cursor.getLong(cursor.getColumnIndex(COL_DATE_POSTED)), 0, ZoneOffset.UTC)
                    )

                    listing.Images = dbHelper.images.getAll().filter { it.ListingId == listing.Id }
                    result.add(listing)
                }
                while (cursor.moveToNext())
            }
        }
        catch (e: SQLiteException)
        {
            db.execSQL(SELECT_QUERY)
            return ArrayList()
        }
        finally
        {
            cursor?.close()
        }

        return result
    }

    override fun add(instance: Listing): Long
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_NAME, instance.Name)
        contentValues.put(COL_ADDRESS, instance.Address)
        contentValues.put(COL_GMAP, instance.GMapLink)
        contentValues.put(COL_PRICE_MIN, instance.PriceMin)
        contentValues.put(COL_PRICE_MAX, instance.PriceMax)
        contentValues.put(COL_RATING, 0.0)
        contentValues.put(COL_DATE_POSTED, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))

        val success = db.insert(TBL_NAME, null, contentValues)
        return success
    }

    override fun update(instance: Listing): Int
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_NAME, instance.Name)
        contentValues.put(COL_ADDRESS, instance.Address)
        contentValues.put(COL_GMAP, instance.GMapLink)
        contentValues.put(COL_PRICE_MIN, instance.PriceMin)
        contentValues.put(COL_PRICE_MAX, instance.PriceMax)
        contentValues.put(COL_RATING, instance.Rating)

        val success = db.update(TBL_NAME, contentValues, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        return success
    }

    override fun delete(instance: Listing): Int
    {
        val db = dbHelper.writableDatabase
        val success = db.delete(TBL_NAME, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        return success
    }
}