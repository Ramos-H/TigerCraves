package com.itg3.grp1.mobdevproject.DbTables

import android.database.sqlite.SQLiteDatabase
import com.itg3.grp1.mobdevproject.DatabaseHelper
import com.itg3.grp1.mobdevproject.models.IModel

abstract class IDbTable<T: IModel>(newDbHelper: DatabaseHelper)
{
    val dbHelper = newDbHelper

    abstract fun createTable(db: SQLiteDatabase?)
    abstract fun dropTable(db: SQLiteDatabase?)
    abstract fun getOne(id: Int) : T?
    abstract fun getAll() : List<T>
    abstract fun add(instance: T) : Long
    abstract fun update(instance: T) : Int
    abstract fun delete(instance: T) : Int
}