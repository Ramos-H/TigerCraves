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

    object Listing
    {
        val TABLE_NAME = "Listings"
        val COL_ID = "Id"
        val COL_POSTER = "Poster"
        val COL_NAME = "Name"
        val COL_ADDRESS = "Address"
        val COL_PRICE_MIN = "PriceMin"
        val COL_PRICE_MAX = "PriceMax"
        val COL_RATING = "Rating"
        val COL_DATE_POSTED = "DatePosted"
    }
}