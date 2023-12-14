package com.itg3.grp1.tigercraves.data.models

import java.time.LocalDateTime

data class User(
    var Id: Int? = null,
    var NameFirst: String,
    var NameMiddle: String?,
    var NameLast: String,
    var Email: String,
    var PasswordHash: String,
    var DateRegistered: LocalDateTime? = null
) : IModel