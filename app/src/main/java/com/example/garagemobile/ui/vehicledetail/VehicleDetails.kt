package com.example.garagemobile.ui.vehicledetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.garagemobile.model.VehicleRepo

@Composable
fun VehicleDetail(
    vehicleId: Long,
    upPress: () -> Unit
) {
    val vehicle = remember(vehicleId) { VehicleRepo.getVehicle(vehicleId) }
    Surface(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("" + vehicle.id)
            Text(vehicle.name)
        }
    }
}