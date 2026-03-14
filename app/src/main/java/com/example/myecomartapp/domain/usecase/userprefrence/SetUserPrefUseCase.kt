package com.example.myecomartapp.domain.usecase.userprefrence

import com.example.myecomartapp.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class SetUserPrefUseCase @Inject constructor(private val  userPreferenceRepository: UserPreferenceRepository){

    suspend fun setFirstTimeLogin(isFirstTimeLogin: Boolean /*false*/) = userPreferenceRepository.setFirstTimeLogin(isFirstTimeLogin)


    suspend fun setLoggedIn(isLoggedIn: Boolean /*true*/) = userPreferenceRepository.setLoggedIn(isLoggedIn)



}
