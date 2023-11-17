package com.itg3.grp1.mobdevproject.DbTables

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.models.IModel
import com.itg3.grp1.mobdevproject.models.Review

class Reviews(dbHandler: SQLiteOpenHelper) : IDbTable<Review>(dbHandler)
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

    override fun getAll(): List<Review>
    {
        TODO("Not yet implemented")
    }

    override fun add(instance: Review): Long
    {
        TODO("Not yet implemented")
    }

    override fun update(instance: Review): Long
    {
        TODO("Not yet implemented")
    }

    override fun delete(instance: Review): Long
    {
        TODO("Not yet implemented")
    }


}