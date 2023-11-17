package com.itg3.grp1.mobdevproject.models

import java.util.Date

data class Listing(
    var Id: Int? = null,
    var Poster: User,
    var Name: String,
    var Address: String,
    var PriceMin: Double?,
    var PriceMax: Double?,
    var Rating: Double?,
    var DatePosted: Date? = null
) : IModel
