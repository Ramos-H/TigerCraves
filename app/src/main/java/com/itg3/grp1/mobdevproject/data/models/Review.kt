package com.itg3.grp1.mobdevproject.data.models

import java.time.LocalDateTime

data class Review(
    var Id: Int? = null,
    var Poster: User,
    var Listing: Listing,
    var Rating: Double,
    var Title: String,
    var Content: String,
    var DatePosted: LocalDateTime? = null
) : IModel
