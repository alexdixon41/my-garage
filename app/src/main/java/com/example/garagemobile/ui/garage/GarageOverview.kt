package com.example.garagemobile.ui.garage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.garagemobile.R
import com.example.garagemobile.model.Vehicle
import com.example.garagemobile.ui.theme.GarageMobileTheme

@Composable
fun GarageOverview(
    onVehicleClick: (Long) -> Unit
) {
    GarageOverview(
        SampleData.garageSample,
        onVehicleClick
    )
}

@Composable
private fun GarageOverview(
    vehicles: List<Vehicle>,
    onVehicleClick: (Long) -> Unit
) {
    LazyColumn {
        items(vehicles) {vehicle ->
            VehicleCard(vehicle, onVehicleClick)
        }
    }
}

@Composable
fun VehicleCard(
    vehicle: Vehicle,
    onVehicleClick: (Long) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = { onVehicleClick(vehicle.id) })
                .padding(all = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.m),
                contentDescription = vehicle.summary,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = vehicle.name,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = vehicle.summary,
                    modifier = Modifier.padding(vertical = 4.dp),
                    maxLines = 2
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    GarageMobileTheme {
        GarageOverview(
            SampleData.garageSample,
            onVehicleClick = { }
        )
    }
}