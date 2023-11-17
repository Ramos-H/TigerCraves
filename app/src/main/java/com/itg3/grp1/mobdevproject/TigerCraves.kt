package com.itg3.grp1.mobdevproject

import android.provider.BaseColumns

object TigerCraves
{
    object User
    {
        val TABLE_NAME = "Users"
        val COL_ID = "Id"
        val COL_NAME_FIRST = "NameFirst"
        val COL_NAME_MIDDLE = "NameMiddle"
        val COL_NAME_LAST = "NameLast"
        val COL_EMAIL = "Email"
        val COL_PASSWORD_HASH = "PasswordHash"
        val COL_DATE_REGISTERED = "DateRegistered"
    }
}