package com.itg3.grp1.mobdevproject.DbTables

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.models.IModel

abstract class IDbTable<T: IModel>(newDbHandler: SQLiteOpenHelper)
{
    val dbHandler = newDbHandler

    abstract fun createTable(db: SQLiteDatabase?)
    abstract fun dropTable(db: SQLiteDatabase?)
    abstract fun getAll()
    abstract fun add(instance: T)
    abstract fun update(instance: T)
    abstract fun delete(instance: T)
}