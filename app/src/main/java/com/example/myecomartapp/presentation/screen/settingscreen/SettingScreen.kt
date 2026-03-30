package com.example.myecomartapp.presentation.screen.settingscreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myecomartapp.presentation.screen.homescreen.HomeScreen


@Composable
fun SettingScreen(navController: NavController) {

    HomeScreen(
        navController = navController,
        home = {}
    )
}