package com.example.myecomartapp.domain.usecase.userprefrence

import com.example.myecomartapp.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPrefUseCase @Inject constructor(private val userPreferenceRepository: UserPreferenceRepository) {

    fun isFirstTimeLogin(): Flow<Boolean> = userPreferenceRepository.isFirstTimeLogin /*false*/
    fun isLoggedIn(): Flow<Boolean> = userPreferenceRepository.isLoggedIn /*true*/
}