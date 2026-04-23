package com.example.myecomartapp.domain.repository

import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.model.SettingProfileState
import kotlinx.coroutines.flow.Flow


interface  SettingRepository {

    suspend fun saveProfile(userProfile: SettingProfileState) : Result<Unit>

    suspend fun getProfile(userId: String)  : Flow<Result<SettingProfileState>>

    suspend fun updateProfile(userId : String, updates: Map<String, Any>): Result<Unit>


}