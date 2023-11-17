package com.itg3.grp1.mobdevproject.models

import java.util.Date

data class User(
    var Id: Int? = null,
    var NameFirst: String,
    var NameMiddle: String?,
    var NameLast: String,
    var Email: String,
    var PasswordHash: String,
    var DateRegistered: Date? = null
)