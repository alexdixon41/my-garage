package com.example.garagemobile.model

import androidx.compose.runtime.Immutable

@Immutable
data class VehicleCollection(
    val id: Long,
    val name: String,
    val vehicles: List<Vehicle>
)

object VehicleRepo {
    fun getVehicles(): List<Vehicle> = vehicles
    fun getVehicle(vehicleId: Long): Vehicle = vehicles.find { it.id == vehicleId }!!
}

val vehicles = SampleData.garageSample
