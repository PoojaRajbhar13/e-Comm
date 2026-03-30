package com.example.myecomartapp.data.model

data class UserPreferenceState(
    val isFirstTimeLogin: Boolean = false,
    val isLoggedIn: Boolean = false, //true
    val isLoading: Boolean = true
)
