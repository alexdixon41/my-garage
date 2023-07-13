package com.example.garagemobile.model

import androidx.compose.runtime.Immutable

@Immutable
data class Vehicle(
    val id: Long,
    val name: String,
    val summary: String
)