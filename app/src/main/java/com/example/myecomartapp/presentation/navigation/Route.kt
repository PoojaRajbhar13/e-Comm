package com.example.myecomartapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object SignUp : Route()

    @Serializable
    data object SplashScreen : Route()

    @Serializable
    data object Login : Route()

    @Serializable
    data object Onboarding : Route()

    @Serializable
    data object HomeScreen : Route()

    @Serializable
    data object SearchScreen : Route()

    @Serializable
    data object Wishlist : Route()

    @Serializable
    data object Cart : Route()

    @Serializable
    data object Settings : Route()


}


