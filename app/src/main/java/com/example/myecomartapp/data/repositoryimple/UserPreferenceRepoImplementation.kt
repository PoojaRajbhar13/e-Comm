package com.example.myecomartapp.data.repositoryimple

import android.content.Context
import com.example.myecomartapp.data.local.UserPreferenceDataStore
import com.example.myecomartapp.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferenceRepoImplementation @Inject constructor(private  val userPreferenceDataStore: UserPreferenceDataStore) : UserPreferenceRepository {

    override val isFirstTimeLogin: Flow<Boolean > = userPreferenceDataStore.isFirstTimeLogin
    override val isLoggedIn: Flow<Boolean> = userPreferenceDataStore.isLoggedIn

     override suspend fun  setFirstTimeLogin(isFirstTimeLogin: Boolean /*false*/){
        userPreferenceDataStore.setFirstTimeLogin(isFirstTimeLogin /*false*/)
    }


    override suspend fun setLoggedIn(isLoggedIn: Boolean /*true*/){
        userPreferenceDataStore.setLoggedIn(isLoggedIn /*true*/)
    }


}