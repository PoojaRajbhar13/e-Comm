package com.example.myecomartapp.presentation.screen.searchscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myecomartapp.presentation.screen.homescreen.HomeScreen
import io.ktor.websocket.Frame


@Composable
fun SearchScreen(navController: NavController) {
    HomeScreen(
        navController = navController,
        home = {}

    )
}

