package com.example.myecomartapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.example.myecomartapp.presentation.viewmodel.CartViewModel
import com.example.myecomartapp.presentation.viewmodel.FavouriteViewModel
import com.example.myecomartapp.presentation.viewmodel.ProductViewModel
import com.example.myecomartapp.presentation.viewmodel.SettingProfileViewModel
import com.example.myecomartapp.presentation.viewmodel.UserPreferenceViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val userPreferenceViewModel: UserPreferenceViewModel = hiltViewModel()
    val productViewModel: ProductViewModel = hiltViewModel()
    val settingProfileViewModel: SettingProfileViewModel = hiltViewModel()
    val favouriteViewModel: FavouriteViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()


    NavHost(navController = navController, startDestination = Route.SplashScreen ){

        composable<Route.SplashScreen>{
            SplashScreen(
                navHostController = navController,
                userPreferenceViewModel = userPreferenceViewModel
            )
        }

        composable<Route.Login>{
            LoginScreen(authViewModel, navHostController = navController)
        }

        composable<Route.SignUp> {
            SignUpScreen(authViewModel, navHostController = navController )
        }

        composable<Route.Onboarding> {
            OnboardingScreen(navHostController = navController, userPreferenceViewModel = userPreferenceViewModel)
        }

        composable<Route.HomeScreen> {
            HomePage(
                navController = navController,
                productViewModel = productViewModel,
                settingProfileViewModel = settingProfileViewModel,
                favouriteViewModel = favouriteViewModel
            )
        }

        composable<Route.SearchScreen> {
            SearchScreen(
                navController = navController,
                searchViewModel = productViewModel,
                settingProfileViewModel = settingProfileViewModel,
                favouriteViewModel = favouriteViewModel
            )
        }

        composable<Route.Settings> {
            SettingScreen(navController, settingProfileViewModel = settingProfileViewModel)
        }

        composable<Route.Wishlist> {
            WishListScreen(
                navController, settingProfileViewModel = settingProfileViewModel,
                favouriteViewModel = favouriteViewModel
            )
        }

        composable<Route.Cart>{
            CartScreen(
                onNavigateBack = { navController.popBackStack() },
                cartViewModel = cartViewModel,
                navController = navController,
                favouriteViewModel = favouriteViewModel,

            )
        }

        composable<Route.ProductDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.ProductDetails>()
            ProductDetailScreen(
                productId = args.ProductId ?: 0,
                productViewModel = productViewModel,
                navController = navController,
                favouriteViewModel = favouriteViewModel,
                cartViewModel = cartViewModel
            )
        }
    }
}
