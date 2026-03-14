package com.example.myecomartapp.presentation.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myecomartapp.presentation.navigation.Route
import com.example.myecomartapp.presentation.viewmodel.UserPreferenceViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    userPreferenceViewModel: UserPreferenceViewModel,
    navHostController: NavHostController
) {
    val state by userPreferenceViewModel.userPreferenceState.collectAsState()
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAni = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000)
    )

    LaunchedEffect(key1 = state.isLoading) {
        startAnimation = true
        // Wait for the splash screen animation to finish
        delay(3000)

        // Only navigate once preferences are loaded
        if (!state.isLoading) {
            when {
                state.isLoggedIn -> {
                    navHostController.navigate(Route.HomeScreen) {
                        popUpTo(Route.SplashScreen) { inclusive = true }
                    }
                }
                state.isFirstTimeLogin -> {
                    navHostController.navigate(Route.Onboarding) {
                        popUpTo(Route.SplashScreen) { inclusive = true }
                    }
                }
                else -> {
                    navHostController.navigate(Route.Onboarding){
                        popUpTo(Route.SplashScreen) {inclusive = true}
                    }

                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alphaAni.value),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.example.myecomartapp.R.drawable.applogo),
                contentDescription = "App Logo",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}
