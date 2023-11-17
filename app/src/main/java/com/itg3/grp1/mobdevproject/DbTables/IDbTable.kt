package com.itg3.grp1.mobdevproject.DbTables

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itg3.grp1.mobdevproject.models.IModel

abstract class IDbTable(dbHandler: SQLiteOpenHelper)
{
    abstract fun createTable(db: SQLiteDatabase?)
    abstract fun dropTable(db: SQLiteDatabase?)
    abstract fun getAll()
    abstract fun add(instance: IModel)
    abstract fun update(instance: IModel)
    abstract fun delete(instance: IModel)
}