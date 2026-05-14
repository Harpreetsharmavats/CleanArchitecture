package com.example.cleanarchitecture.domain.model

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: Int,
    val state: String,
    val street: Street,
    val timezone: Timezone
)