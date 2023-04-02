package com.example.week5.utility

import com.example.week5.network.Address
import com.example.week5.network.Employment
import com.example.week5.network.Subscription
import com.example.week5.network.UserModel

val employment = Employment(title = "Software Engineer")
val address = Address(
    city = "San Francisco",
    country = "USA",
    state = "CA",
    streetAddress = "123 Main St",
    streetName = "Main St",
    zipCode = "12345"
)
val subscription = Subscription(
    plan = "Premium",
    status = "Active",
    term = "Annual"
)
val userModel = UserModel(
    address = address,
    avatar = "https://example.com/avatar.jpg",
    email = "johndoe@example.com",
    employment = employment,
    firstName = "John",
    id = 123,
    lastName = "Doe",
    subscription = subscription,
    username = "johndoe123"
)
