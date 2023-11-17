package com.itg3.grp1.mobdevproject.DbTables

import android.database.sqlite.SQLiteDatabase
import com.itg3.grp1.mobdevproject.models.IModel

class Listings : IDbTable
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
                "$COL_DATE_POSTED INTEGER DEFAULT(unixepoch()) NOT NULL, " +
                "FOREIGN KEY ($COL_POSTER) REFERENCES ${Users.TBL_NAME}(${Users.COL_ID}) ON DELETE CASCADE" +
                ")"

        db?.execSQL(SQL_TBL_CREATE)
    }

    override fun dropTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_DROP = "DROP TABLE IF EXISTS $TBL_NAME"
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