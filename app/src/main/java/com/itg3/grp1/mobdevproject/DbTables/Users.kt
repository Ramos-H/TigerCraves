package com.itg3.grp1.mobdevproject.DbTables

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.DatabaseHandler
import com.itg3.grp1.mobdevproject.models.User
import java.util.Date

class Users(newDbHandler: DatabaseHandler) : IDbTable<User>(newDbHandler)
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

    @SuppressLint("Range")
    override fun getOne(id: Int): User?
    {
        val SELECT_QUERY = "SELECT * FROM $TBL_NAME WHERE $COL_ID = ?"
        val db = dbHandler.readableDatabase
        var result: User? = null
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(SELECT_QUERY, arrayOf(id.toString()))

            if(cursor.moveToFirst())
            {
                result = User(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_NAME_FIRST)),
                    cursor.getString(cursor.getColumnIndex(COL_NAME_MIDDLE)),
                    cursor.getString(cursor.getColumnIndex(COL_NAME_LAST)),
                    cursor.getString(cursor.getColumnIndex(COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COL_PASSWORD_HASH)),
                    Date(cursor.getLong(cursor.getColumnIndex(COL_DATE_REGISTERED)))
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
    override fun getAll() : List<User>
    {
        val result = ArrayList<User>()
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
                    val user = User(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME_FIRST)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME_MIDDLE)),
                        cursor.getString(cursor.getColumnIndex(COL_NAME_LAST)),
                        cursor.getString(cursor.getColumnIndex(COL_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(COL_PASSWORD_HASH)),
                        Date(cursor.getLong(cursor.getColumnIndex(COL_DATE_REGISTERED)))
                    )
                    result.add(user)
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

    override fun update(instance: User) : Int
    {
        val db = dbHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COL_NAME_FIRST, instance.NameFirst)
        contentValues.put(COL_NAME_MIDDLE, instance.NameMiddle)
        contentValues.put(COL_NAME_LAST, instance.NameLast)
        contentValues.put(COL_EMAIL, instance.Email)
        contentValues.put(COL_PASSWORD_HASH, instance.PasswordHash)

        val success = db.update(TBL_NAME, contentValues, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        return success
    }

    override fun delete(instance: User) : Int
    {
        val db = dbHandler.writableDatabase
        val success = db.delete(TBL_NAME, "$COL_ID = ?", arrayOf(instance.Id.toString()))
        return success
    }
}