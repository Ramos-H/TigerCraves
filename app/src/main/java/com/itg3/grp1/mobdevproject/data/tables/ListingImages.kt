package com.itg3.grp1.mobdevproject.data.tables

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.itg3.grp1.mobdevproject.data.DatabaseHelper
import com.itg3.grp1.mobdevproject.data.models.ListingImage

class ListingImages(dbHandler: DatabaseHelper) : DbTable<ListingImage>(dbHandler)
{
    companion object
    {
        val TBL_NAME = "Images"
        val COL_ID = "Id"
        val COL_LISTING = "Listing"
        val COL_RESOURCE_ID = "ResourceId"
    }

    override fun createTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_CREATE_IMAGE = "CREATE TABLE $TBL_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY, " +
                "$COL_LISTING INTEGER NOT NULL, " +
                "$COL_RESOURCE_ID INTEGER NOT NULL, " +
                "FOREIGN KEY ($COL_LISTING) REFERENCES ${Listings.TBL_NAME}(${Listings.COL_ID}) ON DELETE CASCADE" +
                ")"

        db?.execSQL(SQL_TBL_CREATE_IMAGE)
    }

    override fun dropTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_DROP = "DROP TABLE IF EXISTS ${TBL_NAME}"
        db?.execSQL(SQL_TBL_DROP)
    }

    @SuppressLint("Range")
    override fun getOne(id: Int): ListingImage?
    {
        val SELECT_QUERY = "SELECT * FROM $TBL_NAME WHERE $COL_ID = ?"
        val db = dbHelper.readableDatabase
        var result: ListingImage? = null
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(SELECT_QUERY, arrayOf(id.toString()))

            if(cursor.moveToFirst())
            {
                result = ListingImage(
                    cursor.getInt(cursor.getColumnIndex(Reviews.COL_ID)),
                    cursor.getInt(cursor.getInt(cursor.getColumnIndex(COL_LISTING))),
                    cursor.getInt(cursor.getColumnIndex(COL_RESOURCE_ID))
                )
            }
        }
        finally
        {
            cursor?.close()
        }
        return result
    }

    @SuppressLint("Range")
    override fun getAll(): List<ListingImage>
    {
        val result = ArrayList<ListingImage>()
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
                    result.add(ListingImage(
                        cursor.getInt(cursor.getColumnIndex(Reviews.COL_ID)),
                        cursor.getInt(cursor.getInt(cursor.getColumnIndex(COL_LISTING))),
                        cursor.getInt(cursor.getColumnIndex(COL_RESOURCE_ID))
                    ))
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

    override fun delete(instance: ListingImage): Int
    {
        val db = dbHelper.writableDatabase
        val success = db.delete(TBL_NAME, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        return success
    }

    override fun update(instance: ListingImage): Int
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_LISTING, instance.ListingId)
        contentValues.put(COL_RESOURCE_ID, instance.ResourceId)

        val success = db.update(TBL_NAME, contentValues, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        return success
    }

    override fun add(instance: ListingImage): Long
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_LISTING, instance.ListingId)
        contentValues.put(COL_RESOURCE_ID, instance.ResourceId)

        val success = db.insert(TBL_NAME, null, contentValues)
        return success
    }
}