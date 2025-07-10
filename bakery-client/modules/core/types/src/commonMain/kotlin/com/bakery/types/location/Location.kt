package com.bakery.types.location

data class Location(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val precision: Float = 0.0f,
    val altitude: Double = 0.0,
    val time: Long = 0L,
    val city: String = "",
    val country: String = "",
    val zipCode: String = "",
    val state: String = "",
    val streetNumber: String = "",
    val streetName: String = "",
    val streetAddress: String = "$streetNumber $streetName",
)
