package com.example.myecomartapp.presentation.screen.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.myecomartapp.presentation.common.BottomNavigationBar
import com.example.myecomartapp.presentation.homecomponent.HomeTopBar
import com.example.myecomartapp.presentation.navigation.Route
import com.example.myecomartapp.presentation.viewmodel.SettingProfileViewModel


@Composable
fun HomeScreen(
    settingProfileViewModel: SettingProfileViewModel,
    navController: NavController,
    home: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                settingProfileViewModel = settingProfileViewModel,
                onListClick = {},
                onProfileClick = {navController.navigate(Route.Settings)},
                onCartClick = {navController.navigate(Route.Cart)}
            )
        },
        bottomBar = {
            BottomNavigationBar(

                currentRoute = Route.HomeScreen,
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(Route.HomeScreen) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)

        ) {
            home()
        }
    }
}
