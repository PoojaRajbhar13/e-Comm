package com.example.myecomartapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {


    val isFirstTimeLogin: Flow<Boolean>

    val isLoggedIn : Flow<Boolean>

    suspend fun  setFirstTimeLogin(isFirstTimeLogin: Boolean)

    suspend fun setLoggedIn(isLoggedIn: Boolean)

}