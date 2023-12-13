package com.itg3.grp1.mobdevproject.data

import com.itg3.grp1.mobdevproject.data.models.User

class DatabaseSeeder(val databaseHelper: DatabaseHelper)
{
    fun seedUsers()
    {
        val users = listOf<User>(
            User(1, "John", "Allan", "Doe", "johndoe123@gmail.com", "JohnDoe!321"),
            User(2, "Alice", "Michelle", "Johnson", "alice.johnson@gmail.com", "iLuvCats22!"),
            User(3, "Emily", "Nazareth", "Cruz", "ecruz32@gmail.com", "2K21emily"),
            User(4, "Rico", "Jesus", "Puno", "ricoj@gmail.com", "cocoJ18!"),
            User(5, "Sarah", "Mendez", "Santos", "sms14@gmail.com", "SMS2K23"),
            User(6, "Michael", "Eri", "Samuel", "mike23@gmail.com", "likeMike23!"),
            User(7, "Gabriel", "Galvez", "Gonzales", "gabotecue@gmail.com", "gabgab777"),
            User(8, "Jermaine", "Cruz", "Galindez", "jermain3@gmail.com", "maineGal444"),
            User(9, "Tim", "Thompson", "Taureen", "timta@gmail.com", "t9402"),
            User(10, "Olivia", "Wilson", "Santos", "olivia.wilson@gmail.com", "oliveisGr33n")
        )

        users.forEach { databaseHelper.users.add(it) }
    }
}