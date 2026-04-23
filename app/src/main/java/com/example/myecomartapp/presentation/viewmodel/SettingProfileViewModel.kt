package com.example.myecomartapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.model.SettingProfileState
import com.example.myecomartapp.domain.model.SettingState
import com.example.myecomartapp.domain.repository.SettingRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingProfileViewModel @Inject constructor(
    val settingRepository: SettingRepository,
    val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    init{
        loadUserData()
        loadUserProfile()
    }

    fun loadUserData() {
        val currentUser = firebaseAuth.currentUser
        val userProfileUrl = currentUser?.photoUrl?.toString()
        val email = currentUser?.email ?: ""
        _state.value = _state.value.copy(
            userProfile = _state.value.userProfile.copy(email = email),
            profileUrl =  userProfileUrl

        )
    }

    fun loadUserProfile(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val userId = firebaseAuth.currentUser?.uid ?: return@launch
            settingRepository.getProfile(userId).collect {  result ->

                when (result) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            userProfile = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Result.Failure -> {
                        _state.value = _state.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    } else -> {}

                }

            }


        }
    }

    fun updateUserProfile(userProfile: SettingProfileState) {
        viewModelScope.launch {
            try {
                val userId = firebaseAuth.currentUser?.uid
                if (userId == null) {
                    _state.value = _state.value.copy(
                        isSaving = false,
                        saveSuccess = false,
                        error = "user not logged in"
                    )
                    return@launch
                }
                _state.value = _state.value.copy(isSaving = true, error = null)
                val profileWithUserId = userProfile.copy(userId = userId)
                when(val result = settingRepository.saveProfile(userProfile = profileWithUserId)){
                    is Result.Success ->{
                        _state.value = _state.value.copy(
                            isSaving = false,
                            saveSuccess = true,
                            error = null,
                            isLoading = false
                        )

                    }

                    is Result.Failure ->{
                        _state.value = _state.value.copy(
                            isSaving = false,
                            saveSuccess = false,
                            error = result.message,
                            isLoading = false

                        )
                    }else ->{
                        _state.value = _state.value.copy(
                            isSaving = false,
                            saveSuccess = false,
                            error = "Unknown error occurred",
                            isLoading = false
                        )
                    }

                }

            }catch (e: Exception){
                _state.value = _state.value.copy(
                    isSaving = false,
                    saveSuccess = false,
                    error = e.message?: "Unknown error",
                    isLoading = false
                )

            }
        }

    }

    fun resetSaveSuccess(){
        _state.value = _state.value.copy(saveSuccess = false)
    }

    fun clearError(){
   _state.value = _state.value.copy(error = null)
    }
    }




