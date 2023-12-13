package com.itg3.grp1.mobdevproject.data

import com.itg3.grp1.mobdevproject.data.models.Listing
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

    fun seedListings()
    {
        val listings = listOf<Listing>(
            Listing(
                1,
                "Dimsum Treats",
                "cor Dapitan St, Sampaloc, Manila, 1008 Metro Manila",
                "https://maps.app.goo.gl/Mi6aJkqdMJeYVYxb9",
                50.0,
                300.0,
                null
            ),
            Listing(
                2,
                "TigerWinx",
                "1240 Concepcion St, Sampaloc, Manila, 1015 Metro Manila",
                "https://maps.app.goo.gl/yaEdhWX6KBV3iyXW6",
                170.0,
                430.0,
                null
            ),
            Listing(
                3,
                "Barcollie",
                "1161 Padre Noval St, Sampaloc, Manila, 1015 Metro Manila",
                "https://maps.app.goo.gl/SYR5AMJLmagNP9h1A",
                90.0,
                100.0,
                null
            ),
            Listing(
                4,
                "Uncle John's",
                "JX5V+254, Loyola St, Sampaloc, Manila, 1008 Metro Manila",
                "https://maps.app.goo.gl/p6nuZZDJyxesYpKFA",
                28.0,
                120.0,
                null
            ),
            Listing(
                5,
                "Jollibee",
                "Level 1, ONE GRAND CENTRE Espana Blvd, Corner F Jhocson St, Manila City, Metro Manila",
                "https://maps.app.goo.gl/qUjeFxfezrhgG6mq8",
                75.0,
                970.0,
                null
            ),
            Listing(
                6,
                "Manila Cafe",
                "Annie's Place Bldg., 1015 P. Noval Street Brgy. 468, Sampaloc, Manila",
                "https://maps.app.goo.gl/mqdEYJpQc3VbGX2P6",
                65.0,
                400.0,
                null
            ),
            Listing(
                7,
                "Ramen Avenue",
                "789 Padre Noval St, Sampaloc, Manila, 1008 Metro Manila",
                "https://maps.app.goo.gl/YrQxEQBv3i7jotDg7",
                69.0,
                200.0,
                null
            ),
            Listing(
                8,
                "Macao Imperial Tea",
                "Pacific Suites, 1218 Santander St, Sampaloc, Manila, 1008 Metro Manila",
                "https://maps.app.goo.gl/KJ1sMdE18WxkyAR87",
                90.0,
                195.0,
                null
            ),
            Listing(
                9,
                "McDonalds",
                "JX6V+6H5, Espana Blvd, corner Carola, Don Quijote St, Sampaloc, Manila, 1008 Metro Manila",
                "https://maps.app.goo.gl/ymEEH7QV74rzcikp8",
                70.0,
                690.0,
                null
            ),
            Listing(
                10,
                "KFC",
                "Zone 46, 1240 Dapitan St. Cor. V, Concepcion St, Sampaloc, Manila, 1008 Metro Manila",
                "https://maps.app.goo.gl/gkrgHJ6RxZzrXJqr6",
                77.0,
                810.0,
                null
            )
        )
        listings.forEach { databaseHelper.listings.add(it) }
    }
}