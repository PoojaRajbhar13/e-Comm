package com.example.myecomartapp.presentation.screen.wishlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myecomartapp.presentation.navigation.Route
import com.example.myecomartapp.presentation.screen.homescreen.HomeScreen


@Composable
fun  WishListScreen(navController: NavHostController){
 HomeScreen(
     navController = navController,
     home = {
         Text("Wishlist")
     }
 )
}