package com.itg3.grp1.mobdevproject.DbTables

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.models.IModel

class Users(dbHandler: SQLiteOpenHelper) : IDbTable(dbHandler)
{
    companion object
    {
        val TBL_NAME = "Users"
        val COL_ID = "Id"
        val COL_NAME_FIRST = "NameFirst"
        val COL_NAME_MIDDLE = "NameMiddle"
        val COL_NAME_LAST = "NameLast"
        val COL_EMAIL = "Email"
        val COL_PASSWORD_HASH = "PasswordHash"
        val COL_DATE_REGISTERED = "DateRegistered"
    }

    override fun createTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_CREATE = "CREATE TABLE $TBL_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "$COL_NAME_FIRST TEXT NOT NULL, " +
                "$COL_NAME_MIDDLE TEXT, " +
                "$COL_NAME_LAST TEXT NOT NULL, " +
                "$COL_EMAIL TEXT NOT NULL, " +
                "$COL_PASSWORD_HASH TEXT NOT NULL, " +
                "$COL_DATE_REGISTERED INTEGER DEFAULT(unixepoch()) NOT NULL" +
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