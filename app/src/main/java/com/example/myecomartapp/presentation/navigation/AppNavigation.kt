package com.example.myecomartapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myecomartapp.presentation.screen.HomeScreen
import com.example.myecomartapp.presentation.screen.auth.LoginScreen
import com.example.myecomartapp.presentation.screen.auth.SignUpScreen
import com.example.myecomartapp.presentation.screen.onboarding.OnboardingScreen
import com.example.myecomartapp.presentation.screen.splash.SplashScreen
import com.example.myecomartapp.presentation.viewmodel.AuthViewModel
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(){
     val navController = rememberNavController()
     val viewModel : AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = Route.Login ){

        composable<Route.SplashScreen>{
            SplashScreen(
                onFinish = {
                    navController.navigate(Route.Login){
                        popUpTo(Route.SplashScreen){ inclusive = true}
                    }
                }
            )
        }

        composable<Route.Login>{
            LoginScreen( viewModel, navHostController = navController)
        }

        composable<Route.SignUp> {
            SignUpScreen(viewModel, navHostController = navController )
        }

        composable<Route.Onboarding> {
            OnboardingScreen()
        }

        composable<Route.HomeScreen> {
            HomeScreen()
        }
    }
}