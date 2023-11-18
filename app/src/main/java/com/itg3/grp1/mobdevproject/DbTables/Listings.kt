package com.itg3.grp1.mobdevproject.DbTables

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.DatabaseHandler
import com.itg3.grp1.mobdevproject.models.Listing
import com.itg3.grp1.mobdevproject.models.User
import java.util.Date

class Listings(dbHandler: DatabaseHandler) : IDbTable<Listing>(dbHandler)
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

    @SuppressLint("Range")
    override fun getAll(): List<Listing>
    {
        val result = ArrayList<Listing>()
        val SELECT_QUERY = "SELECT * FROM $TBL_NAME"
        val db = dbHandler.readableDatabase
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(SELECT_QUERY, null)
        }
        catch (e: SQLiteException)
        {
            db.execSQL(SELECT_QUERY)
            return ArrayList()
        }

        if(cursor.moveToFirst())
        {
            do
            {
                var posterId = cursor.getInt(cursor.getColumnIndex(COL_POSTER))
                var poster : User = User(posterId, "", "", "", "", "", Date())

                val users = dbHandler.users.getAll()
                for(user in users)
                {
                    if(user.Id == posterId)
                    {
                        poster = user
                        break
                    }
                }

                val listing = Listing(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    poster,
                    cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_ADDRESS)),
                    cursor.getDouble(cursor.getColumnIndex(COL_PRICE_MIN)),
                    cursor.getDouble(cursor.getColumnIndex(COL_PRICE_MAX)),
                    cursor.getDouble(cursor.getColumnIndex(COL_RATING)),
                    Date(cursor.getLong(cursor.getColumnIndex(COL_DATE_POSTED)))
                )

                result.add(listing)
            }
            while (cursor.moveToNext())
        }

        return result
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
        val db = dbHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_NAME, instance.Name)
        contentValues.put(COL_ADDRESS, instance.Address)
        contentValues.put(COL_PRICE_MIN, instance.PriceMin)
        contentValues.put(COL_PRICE_MAX, instance.PriceMax)
        contentValues.put(COL_RATING, instance.Rating)

        val success = db.update(TBL_NAME, contentValues, "${COL_ID} = ?", arrayOf(instance.Id.toString()))
        return success
    }

    override fun delete(instance: Listing): Int
    {
        val db = dbHandler.writableDatabase
        val success = db.delete(TBL_NAME, "${COL_ID} = ?", arrayOf(instance.Id.toString()))
        return success
    }
}