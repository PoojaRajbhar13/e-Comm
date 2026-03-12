package com.example.myecomartapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class  Route{  // subclass

    @Serializable  // use for conversion
    data object SignUp  : Route()

    @Serializable
    data object SplashScreen : Route()

    @Serializable
    data object  Login : Route()

    @Serializable
    data object Onboarding : Route()


    @Serializable
    data object  HomeScreen: Route()

}