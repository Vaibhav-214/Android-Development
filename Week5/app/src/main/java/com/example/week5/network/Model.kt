package com.example.week5.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserModel(
    val address: Address,
    val avatar: String,
    val email: String,
    val employment: Employment,
    @Json(name = "first_name")
    val firstName: String,
    val id: Int,
    @Json(name = "last_name")
    val lastName: String,
    val subscription: Subscription,
    val username: String
)

@JsonClass(generateAdapter = true)
data class Address(
    val city: String,
    val country: String,
    val state: String,

    @Json(name = "street_address")
    val streetAddress: String,
    @Json(name = "street_name")
    val streetName: String,
    @Json(name = "zip_code")
    val zipCode: String
)

@JsonClass(generateAdapter = true)
data class Employment(
    val title: String
)

@JsonClass(generateAdapter = true)
data class Subscription(
    val plan: String,
    val status: String,
    val term: String
)
