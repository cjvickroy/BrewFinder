package com.example.brewfinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Brewery(
    // Brewery details
    @PrimaryKey
    val name: String,
    val brewery_type: String,
    val phone: String?,
    val website_url: String?,
    // Location
    val street: String?,
    val state: String?,
    val city: String?,
    val country: String?,
    val latitude: String?,
    val longitude: String?
) : Serializable