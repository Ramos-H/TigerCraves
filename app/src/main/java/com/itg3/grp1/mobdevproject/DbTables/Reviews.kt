package com.itg3.grp1.mobdevproject.DbTables

import android.database.sqlite.SQLiteDatabase
import com.itg3.grp1.mobdevproject.models.IModel

class Reviews : IDbTable
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
                "$COL_DATE_POSTED INTEGER DEFAULT(unixepoch()) NOT NULL, " +
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

    override fun getAll()
    {
        TODO("Not yet implemented")
    }

    override fun add(instance: IModel)
    {
        TODO("Not yet implemented")
    }

    override fun update(instance: IModel)
    {
        TODO("Not yet implemented")
    }

    override fun delete(instance: IModel)
    {
        TODO("Not yet implemented")
    }
}