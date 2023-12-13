package com.itg3.grp1.mobdevproject.data

import com.itg3.grp1.mobdevproject.data.models.Listing
import com.itg3.grp1.mobdevproject.data.models.Review
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

    fun seedReviews()
    {
        val reviews = listOf<Review>(
            Review(1,databaseHelper.users.getOne(1)!!,databaseHelper.listings.getOne(1)!!,5.0,"Dimsum da best!","Masarap na Affordable pa, very recommended for budget meal !"),
            Review(2,databaseHelper.users.getOne(1)!!,databaseHelper.listings.getOne(2)!!,2.0,"TigerWinx","Medyo mahal siya at di ganon kasarap para sa price nya."),
            Review(3,databaseHelper.users.getOne(1)!!,databaseHelper.listings.getOne(3)!!,5.0,"Barcollie na tayo!","Loved the flavors lalo na yung unli gravy!"),
            Review(4,databaseHelper.users.getOne(2)!!,databaseHelper.listings.getOne(5)!!,4.0,"Jabee parin!","Pag wala na kaming maisip ng friends jabee na agad!"),
            Review(5,databaseHelper.users.getOne(2)!!,databaseHelper.listings.getOne(10)!!,3.0,"KFC ano na?","Ok naman yung KFC kaso pansin ko parang lumiliit na yung serving nila"),
            Review(6,databaseHelper.users.getOne(2)!!,databaseHelper.listings.getOne(9)!!,2.0,"Mcdo nanaman?","medyo maraming tao at mga estudyante na maiingay"),
            Review(7,databaseHelper.users.getOne(3)!!,databaseHelper.listings.getOne(3)!!,5.0,"Barcollie lang sakalam","Dito house vibes talaga tas affordable!"),
            Review(8,databaseHelper.users.getOne(3)!!,databaseHelper.listings.getOne(4)!!,4.0,"Uncle John's enjoyer!","Junior meal ang meta na ngayon may tubig na + chocolate + soup"),
            Review(9,databaseHelper.users.getOne(3)!!,databaseHelper.listings.getOne(8)!!,5.0,"I love it!","mahilig talaga ako sa mga matatamis + affordable na to!"),
            Review(10,databaseHelper.users.getOne(4)!!,databaseHelper.listings.getOne(3)!!,4.0,"Lutong Bahay ba hanap","Recommended yung sisig!"),
            Review(11,databaseHelper.users.getOne(4)!!,databaseHelper.listings.getOne(7)!!,4.0,"sawa na sa dry food?","Maraming affordable na ramen dito pwede na sa price nya"),
            Review(12,databaseHelper.users.getOne(4)!!,databaseHelper.listings.getOne(1)!!,5.0,"Dimsum 3ts gang grumaduate","5/5 na to matik sa mga laking siomai dyan"),
            Review(13,databaseHelper.users.getOne(5)!!,databaseHelper.listings.getOne(5)!!,2.0,"Ang init sa 2nd floor!","ok naman yung food kaso yung 2nd floor walang kurtina tirik yung araw pagkumakain HAHA"),
            Review(14,databaseHelper.users.getOne(5)!!,databaseHelper.listings.getOne(8)!!,1.0,"Not for me","Masyadong matamis para sakin"),
            Review(15,databaseHelper.users.getOne(5)!!,databaseHelper.listings.getOne(4)!!,4.0,"Recommended talaga!","likod lang ng frassatti, affordable at hindi masyado crowded, malamig pa!"),
            Review(16,databaseHelper.users.getOne(6)!!,databaseHelper.listings.getOne(7)!!,1.0,"Not recommended","matagal serving"),
            Review(17,databaseHelper.users.getOne(6)!!,databaseHelper.listings.getOne(6)!!,5.0,"Manila Cafe Sponsor naman","lagi ako dyan! affordable tas malamig pa!"),
            Review(18,databaseHelper.users.getOne(6)!!,databaseHelper.listings.getOne(2)!!,4.0,"Budgetted Group meals","affordable lalo na sa group meals"),
            Review(19,databaseHelper.users.getOne(7)!!,databaseHelper.listings.getOne(1)!!,5.0,"UST's best","matik na 5/5"),
            Review(20,databaseHelper.users.getOne(7)!!,databaseHelper.listings.getOne(3)!!,4.0,"Recommended para sa lutong bahay","masarap kaso mainit yung pwesto lalo na sa 2nd floor"),
            Review(21,databaseHelper.users.getOne(7)!!,databaseHelper.listings.getOne(6)!!,5.0,"#1 study area","matahimik yung people pati affordable na pwede mag aral."),
            Review(22,databaseHelper.users.getOne(8)!!,databaseHelper.listings.getOne(3)!!,1.0,"Wrong service!","mali yung sinerve sakin na pagkain!"),
            Review(23,databaseHelper.users.getOne(8)!!,databaseHelper.listings.getOne(2)!!,5.0,"Da best Wings!","Sarap ng wings nila talaga!"),
            Review(24,databaseHelper.users.getOne(8)!!,databaseHelper.listings.getOne(7)!!,4.0,"Favorite Ramen!","sarap ng ramen!"),
            Review(25,databaseHelper.users.getOne(9)!!,databaseHelper.listings.getOne(8)!!,4.0,"reco. milk teas","dito masasarap ung milk tea"),
            Review(26,databaseHelper.users.getOne(9)!!,databaseHelper.listings.getOne(10)!!,5.0,"KFC gang","san dito yung KFC gangs!"),
            Review(27,databaseHelper.users.getOne(9)!!,databaseHelper.listings.getOne(1)!!,1.0,"overhyped?","overhyped para sakin dimsum treats"),
            Review(28,databaseHelper.users.getOne(10)!!,databaseHelper.listings.getOne(2)!!,3.0,"Good Wings","masarap naman yung wings yung area lang masikip."),
            Review(29,databaseHelper.users.getOne(10)!!,databaseHelper.listings.getOne(6)!!,5.0,"Manila Cafe #1","Affordable na SB variant hahaha"),
            Review(30,databaseHelper.users.getOne(10)!!,databaseHelper.listings.getOne(4)!!,2.0,"Puro manok!?","puro manok e kaumay")
        )
        reviews.forEach { databaseHelper.reviews.add(it) }
    }
}