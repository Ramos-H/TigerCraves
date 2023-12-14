package com.itg3.grp1.mobdevproject.data.models

import java.time.LocalDateTime

data class Listing(
    var Id: Int? = null,
    var Name: String,
    var Address: String,
    var GMapLink: String?,
    var PriceMin: Double?,
    var PriceMax: Double?,
    var Rating: Double?,
    var DatePosted: LocalDateTime? = null,
    var Images: List<ListingImage>? = null
) : IModel
