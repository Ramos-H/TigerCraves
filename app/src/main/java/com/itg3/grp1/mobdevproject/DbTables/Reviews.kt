package com.itg3.grp1.mobdevproject.DbTables

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.itg3.grp1.mobdevproject.DatabaseHandler
import com.itg3.grp1.mobdevproject.models.Listing
import com.itg3.grp1.mobdevproject.models.Review
import com.itg3.grp1.mobdevproject.models.User
import java.util.Date

class Reviews(dbHandler: DatabaseHandler) : IDbTable<Review>(dbHandler)
{
    companion object
    {
        val TBL_NAME = "Reviews"
        val COL_ID = "Id"
        val COL_POSTER = "Poster"
        val COL_LISTING = "Listing"
        val COL_RATING = "Rating"
        val COL_TITLE = "Title"
        val COL_CONTENT = "Content"
        val COL_DATE_POSTED = "DatePosted"
    }

    override fun createTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_CREATE_REVIEW = "CREATE TABLE $TBL_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY, " +
                "$COL_POSTER INTEGER NOT NULL, " +
                "$COL_LISTING INTEGER NOT NULL, " +
                "$COL_RATING REAL, " +
                "$COL_TITLE TEXT, " +
                "$COL_CONTENT TEXT, " +
                "$COL_DATE_POSTED INTEGER NOT NULL, " +
                "FOREIGN KEY ($COL_POSTER) REFERENCES ${Users.TBL_NAME}(${Users.COL_ID}) ON DELETE CASCADE, " +
                "FOREIGN KEY ($COL_LISTING) REFERENCES ${Listings.TBL_NAME}(${Listings.COL_ID}) ON DELETE CASCADE" +
                ")"

        db?.execSQL(SQL_TBL_CREATE_REVIEW)
    }

    override fun dropTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_DROP = "DROP TABLE IF EXISTS ${TBL_NAME}"
        db?.execSQL(SQL_TBL_DROP)
    }

    @SuppressLint("Range")
    override fun getOne(id: Int): Review?
    {
        val SELECT_QUERY = "SELECT * FROM $TBL_NAME WHERE $COL_ID = ?"
        val db = dbHandler.readableDatabase
        var result: Review? = null
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(SELECT_QUERY, arrayOf(id.toString()))

            if(cursor.moveToFirst())
            {
                val posterId = cursor.getInt(cursor.getColumnIndex(COL_POSTER))
                val poster = dbHandler.users.getOne(posterId)

                var listingId = cursor.getInt(cursor.getColumnIndex(COL_LISTING))
                var listing = dbHandler.listings.getOne(listingId)

                result = Review(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    poster!!,
                    listing!!,
                    cursor.getDouble(cursor.getColumnIndex(COL_RATING)),
                    cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                    Date(cursor.getLong(cursor.getColumnIndex(COL_DATE_POSTED)))
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
    override fun getAll(): List<Review>
    {
        val result = ArrayList<Review>()
        val SELECT_QUERY = "SELECT * FROM $TBL_NAME"
        val db = dbHandler.readableDatabase
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(SELECT_QUERY, null)

            if(cursor.moveToFirst())
            {
                do
                {
                    var posterId = cursor.getInt(cursor.getColumnIndex(COL_POSTER))
                    var poster = dbHandler.users.getOne(posterId)

                    var listingId = cursor.getInt(cursor.getColumnIndex(COL_LISTING))
                    var listing = dbHandler.listings.getOne(listingId)

                    val review = Review(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        poster!!,
                        listing!!,
                        cursor.getDouble(cursor.getColumnIndex(COL_RATING)),
                        cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                        Date(cursor.getLong(cursor.getColumnIndex(COL_DATE_POSTED)))
                    )

                    result.add(review)
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

    override fun add(instance: Review): Long
    {
        val db = dbHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_POSTER, instance.Poster.Id)
        contentValues.put(COL_LISTING, instance.Listing.Id)
        contentValues.put(COL_RATING, instance.Rating)
        contentValues.put(COL_TITLE, instance.Title)
        contentValues.put(COL_CONTENT, instance.Content)
        contentValues.put(COL_DATE_POSTED, System.currentTimeMillis())

        val success = db.insert(TBL_NAME, null, contentValues)
        return success
    }

    override fun update(instance: Review): Int
    {
        val db = dbHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_RATING, instance.Rating)
        contentValues.put(COL_TITLE, instance.Title)
        contentValues.put(COL_CONTENT, instance.Content)

        val success = db.update(TBL_NAME, contentValues, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        return success
    }

    override fun delete(instance: Review): Int
    {
        val db = dbHandler.writableDatabase
        val success = db.delete(TBL_NAME, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        return success
    }
}