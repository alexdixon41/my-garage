package com.example.garagemobile.ui

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GarageMobileApp()
        }
    }
}