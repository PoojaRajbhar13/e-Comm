package com.example.myecomartapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myecomartapp.presentation.screen.auth.LoginScreen
import com.example.myecomartapp.presentation.screen.auth.SignUpScreen
import com.example.myecomartapp.presentation.screen.cartscreen.CartScreen
import com.example.myecomartapp.presentation.screen.homescreen.HomePage
import com.example.myecomartapp.presentation.screen.onboarding.OnboardingScreen
import com.example.myecomartapp.presentation.screen.product.ProductDetailScreen
import com.example.myecomartapp.presentation.screen.searchscreen.SearchScreen
import com.example.myecomartapp.presentation.screen.settingscreen.SettingScreen
import com.example.myecomartapp.presentation.screen.splash.SplashScreen
import com.example.myecomartapp.presentation.screen.wishlist.WishListScreen
import com.example.myecomartapp.presentation.viewmodel.AuthViewModel
import com.example.myecomartapp.presentation.viewmodel.ProductViewModel
import com.example.myecomartapp.presentation.viewmodel.UserPreferenceViewModel

@Composable
fun AppNavigation(){
     val navController = rememberNavController()
     val viewModel : AuthViewModel = viewModel()
    val viewModel1 : UserPreferenceViewModel = viewModel()
    val viewModel2 : ProductViewModel = viewModel()

    NavHost(navController = navController, startDestination = Route.SplashScreen ){

        composable<Route.SplashScreen>{
            SplashScreen(
                navHostController = navController,
                userPreferenceViewModel = viewModel1
            )
        }

        composable<Route.Login>{
            LoginScreen( viewModel, navHostController = navController)
        }

        composable<Route.SignUp> {
            SignUpScreen(viewModel, navHostController = navController )
        }

        composable<Route.Onboarding> {
            OnboardingScreen(navHostController = navController, userPreferenceViewModel = viewModel1)
        }

        composable<Route.HomeScreen> {
            HomePage(navController = navController, productViewModel = viewModel2 )
        }

        composable<Route.SearchScreen> {
            SearchScreen(navController, searchViewModel = viewModel2)
        }

        composable<Route.Settings> {
            SettingScreen(navController)
        }

        composable<Route.Wishlist> {
            WishListScreen(navController)
        }

        composable<Route.Cart>{
            CartScreen()
        }

        composable<Route.ProductDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.ProductDetails>()
            ProductDetailScreen(productId = args.ProductId?: 0 /*id = 1*/, productViewModel = viewModel2, navController )
        }
    }
}
