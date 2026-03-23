package com.example.myecomartapp.presentation.screen.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myecomartapp.presentation.homecomponent.HomeTopBar


@Composable
fun HomeScreen( home : @Composable () -> Unit ) {
Scaffold(
    topBar = { HomeTopBar(
        onListClick = {},
        onProfileClick = {}
    )  },
    bottomBar = {}
) { innerPadding ->
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding)

    ){
       home()
    }

}


}