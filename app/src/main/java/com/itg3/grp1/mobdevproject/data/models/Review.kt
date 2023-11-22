package com.itg3.grp1.mobdevproject.data.models

import java.util.Date

data class Review(
    var Id: Int? = null,
    var Poster: User,
    var Listing: Listing,
    var Rating: Double,
    var Title: String,
    var Content: String,
    var DatePosted: Date? = null
) : IModel
