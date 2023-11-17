package com.itg3.grp1.mobdevproject.DbTables

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.models.User

class Users(newDbHandler: SQLiteOpenHelper) : IDbTable<User>(newDbHandler)
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
                "$COL_DATE_REGISTERED INTEGER NOT NULL" +
                ")"
        db?.execSQL(SQL_TBL_CREATE)
    }

    override fun dropTable(db: SQLiteDatabase?)
    {
        val SQL_TBL_DROP = "DROP TABLE IF EXISTS $TBL_NAME"
        db?.execSQL(SQL_TBL_DROP)
    }

    override fun getAll() : List<User>
    {
        TODO("Not yet implemented")
    }

    override fun add(instance: User): Long
    {
        val db = dbHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_NAME_FIRST, instance.NameFirst)
        contentValues.put(COL_NAME_MIDDLE, instance.NameMiddle)
        contentValues.put(COL_NAME_LAST, instance.NameLast)
        contentValues.put(COL_EMAIL, instance.Email)
        contentValues.put(COL_PASSWORD_HASH, instance.PasswordHash)
        contentValues.put(COL_DATE_REGISTERED, System.currentTimeMillis())

        val success = db.insert(TBL_NAME, null, contentValues)
        return success
    }

    override fun update(instance: User) : Long
    {
        TODO("Not yet implemented")
    }

    override fun delete(instance: User) : Long
    {
        TODO("Not yet implemented")
    }
}