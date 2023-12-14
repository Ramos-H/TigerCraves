package com.itg3.grp1.tigercraves.data.tables

import android.database.sqlite.SQLiteDatabase
import com.itg3.grp1.tigercraves.data.DatabaseHelper
import com.itg3.grp1.tigercraves.data.models.IModel

abstract class DbTable<T: IModel>(newDbHelper: DatabaseHelper)
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