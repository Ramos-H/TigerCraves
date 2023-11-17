package com.itg3.grp1.mobdevproject.DbTables

import android.database.sqlite.SQLiteDatabase
import com.itg3.grp1.mobdevproject.models.IModel

interface IDbTable
{
    fun createTable(db: SQLiteDatabase?)
    fun dropTable(db: SQLiteDatabase?)
    fun getAll()
    fun add(instance: IModel)
    fun update(instance: IModel)
    fun delete(instance: IModel)
}