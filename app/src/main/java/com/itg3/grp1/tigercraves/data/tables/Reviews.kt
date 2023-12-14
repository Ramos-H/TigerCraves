package com.itg3.grp1.tigercraves.data.tables

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.itg3.grp1.tigercraves.data.DatabaseHelper
import com.itg3.grp1.tigercraves.data.models.Review
import java.time.LocalDateTime
import java.time.ZoneOffset

class Reviews(dbHandler: DatabaseHelper) : DbTable<Review>(dbHandler)
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
        val SQL_TBL_DROP = "DROP TABLE IF EXISTS $TBL_NAME"
        db?.execSQL(SQL_TBL_DROP)
    }

    @SuppressLint("Range")
    override fun getOne(id: Int): Review?
    {
        val SELECT_QUERY = "SELECT * FROM $TBL_NAME WHERE $COL_ID = ?"
        val db = dbHelper.readableDatabase
        var result: Review? = null
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(SELECT_QUERY, arrayOf(id.toString()))

            if(cursor.moveToFirst())
            {
                val posterId = cursor.getInt(cursor.getColumnIndex(COL_POSTER))
                val poster = dbHelper.users.getOne(posterId)

                var listingId = cursor.getInt(cursor.getColumnIndex(COL_LISTING))
                var listing = dbHelper.listings.getOne(listingId)

                result = Review(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    poster!!,
                    listing!!,
                    cursor.getDouble(cursor.getColumnIndex(COL_RATING)),
                    cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                    LocalDateTime.ofEpochSecond(cursor.getLong(cursor.getColumnIndex(COL_DATE_POSTED)), 0, ZoneOffset.UTC)
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
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(SELECT_QUERY, null)

            if(cursor.moveToFirst())
            {
                do
                {
                    var posterId = cursor.getInt(cursor.getColumnIndex(COL_POSTER))
                    var poster = dbHelper.users.getOne(posterId)

                    var listingId = cursor.getInt(cursor.getColumnIndex(COL_LISTING))
                    var listing = dbHelper.listings.getOne(listingId)

                    val review = Review(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        poster!!,
                        listing!!,
                        cursor.getDouble(cursor.getColumnIndex(COL_RATING)),
                        cursor.getString(cursor.getColumnIndex(COL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COL_CONTENT)),
                        LocalDateTime.ofEpochSecond(cursor.getLong(cursor.getColumnIndex(COL_DATE_POSTED)), 0, ZoneOffset.UTC)
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
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_POSTER, instance.Poster.Id)
        contentValues.put(COL_LISTING, instance.Listing.Id)
        contentValues.put(COL_RATING, instance.Rating)
        contentValues.put(COL_TITLE, instance.Title)
        contentValues.put(COL_CONTENT, instance.Content)
        contentValues.put(COL_DATE_POSTED, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))

        val success = db.insert(TBL_NAME, null, contentValues)
        if(success > -1)
        {
            updateListingAverageRating(instance)
        }
        return success
    }

    override fun update(instance: Review): Int
    {
        val db = dbHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_RATING, instance.Rating)
        contentValues.put(COL_TITLE, instance.Title)
        contentValues.put(COL_CONTENT, instance.Content)

        val success = db.update(TBL_NAME, contentValues, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        if(success > -1)
        {
            updateListingAverageRating(instance)
        }
        return success
    }

    fun updateListingAverageRating(instance: Review)
    {
        val reviews = getAll().filter { it.Listing.Id == instance.Listing.Id }
        if(reviews == null)
        {
            instance.Listing.Rating = 0.0
        }
        else
        {
            instance.Listing.Rating = reviews.sumOf { it.Rating } / reviews.count()
        }

        dbHelper.listings.update(instance.Listing)
    }

    override fun delete(instance: Review): Int
    {

        val db = dbHelper.writableDatabase
        val success = db.delete(TBL_NAME, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        if(success > -1)
        {
            updateListingAverageRating(instance)
        }
        return success
    }
}