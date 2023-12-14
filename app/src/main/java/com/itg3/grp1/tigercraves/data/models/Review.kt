package com.itg3.grp1.tigercraves.data.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Review(
    var Id: Int? = null,
    var Poster: User,
    var Listing: Listing,
    var Rating: Double,
    var Title: String,
    var Content: String,
    var DatePosted: LocalDateTime? = null
) : IModel
{
    fun getSimplifiedReviewDate(): String?
    {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY")
        return DatePosted?.format(formatter)
    }
}