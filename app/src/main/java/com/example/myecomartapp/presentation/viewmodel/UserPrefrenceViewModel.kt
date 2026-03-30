package com.example.myecomartapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myecomartapp.data.model.UserPreferenceState
import com.example.myecomartapp.domain.usecase.userprefrence.GetUserPrefUseCase
import com.example.myecomartapp.domain.usecase.userprefrence.SetUserPrefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserPreferenceViewModel @Inject constructor(
    private val getUserPrefUseCase: GetUserPrefUseCase,
    private val setUserPrefUseCase: SetUserPrefUseCase
) : ViewModel() {

    private val _userPreferenceState = MutableStateFlow(UserPreferenceState())
    val userPreferenceState = _userPreferenceState.asStateFlow()

    init {
        observeUserPreferences()
    }

    private fun observeUserPreferences(){
        viewModelScope.launch {
            combine(
                getUserPrefUseCase.isFirstTimeLogin(), // here we call get userpreference and set  userprefrence in authView model
                getUserPrefUseCase.isLoggedIn()
            ){ isFirstTime /*false*/, isLogged /*true*/ ->
                UserPreferenceState(
                    isFirstTimeLogin = isFirstTime, //false
                    isLoggedIn = isLogged, //true
                    isLoading = false
                )
            }.collect { newState -> 
                _userPreferenceState.value = newState 
            }
        }
    }

    fun onBoardingFinished() {
        viewModelScope.launch {
            setUserPrefUseCase.setFirstTimeLogin(false)
        }
    }
}
