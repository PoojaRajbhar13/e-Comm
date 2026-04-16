package com.example.myecomartapp.presentation.screen.wishlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myecomartapp.presentation.navigation.Route
import com.example.myecomartapp.presentation.screen.homescreen.HomeScreen
import com.example.myecomartapp.presentation.viewmodel.SettingProfileViewModel


@Composable
fun  WishListScreen(navController: NavHostController, settingProfileViewModel: SettingProfileViewModel){
 HomeScreen(
     settingProfileViewModel = settingProfileViewModel,
     navController = navController,
     home = {
         Text("Wishlist")
     }
 )
}