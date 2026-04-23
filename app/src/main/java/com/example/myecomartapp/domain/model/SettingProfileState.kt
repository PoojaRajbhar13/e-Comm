package com.example.myecomartapp.domain.model



data class SettingProfileState(
    val name: String = "",
    val userId: String = "",
    val email: String = "",
    val password: String = "",
    val pincode: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val bankAccountNumber: String = "",
    val accountHolderName: String = "",
    val ifscCode: String = "",
    val  profileUrl: String? = null
)