package com.example.myecomartapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.usecase.GoogleAuthUsecase
import com.example.myecomartapp.domain.usecase.LoginUsecase
import com.example.myecomartapp.domain.usecase.SignupUsecase
import com.example.myecomartapp.domain.usecase.userprefrence.SetUserPrefUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val loginUsecase: LoginUsecase,
    private val signupUsecase: SignupUsecase,
    private val setUserPrefUseCase: SetUserPrefUseCase,
    private val googleAuthUsecase: GoogleAuthUsecase
) : ViewModel(){


    private val _authState = MutableStateFlow<Result<String>>(Result.Idle)

    val authState = _authState.asStateFlow()

    fun login(email: String, password: String){
        _authState.value = Result.Loading
        viewModelScope.launch(Dispatchers.IO){
            try{
                val result = loginUsecase(email, password)
                _authState.value = result
                if(result is Result.Success){
                    setUserPrefUseCase.setFirstTimeLogin(false)
                    setUserPrefUseCase.setLoggedIn(true)
                }
            }catch (e: Exception){
                _authState.value = Result.Failure(e.message ?: "Login failed")
            }
        }
    }

    //signup
    fun signup(email: String, password: String){
        _authState.value = Result.Loading
        viewModelScope.launch(Dispatchers.IO){
            try{
               val result = signupUsecase(email, password)
                _authState.value = result
                if(result is Result.Success) {
                    setUserPrefUseCase.setFirstTimeLogin(false)
                    setUserPrefUseCase.setLoggedIn(true)
                }
            }catch (e:Exception){
                _authState.value = Result.Failure(e.message ?: "SignUp fail")
            }
        }
    }


    fun googleAuth(account: GoogleSignInAccount){
        _authState.value = Result.Loading
        viewModelScope.launch(Dispatchers.IO){
          val result = googleAuthUsecase(account)
            _authState.value = result
            if(result is Result.Success) {
                setUserPrefUseCase.setFirstTimeLogin(false)
                setUserPrefUseCase.setLoggedIn(true)
            }
        }
    }

}
