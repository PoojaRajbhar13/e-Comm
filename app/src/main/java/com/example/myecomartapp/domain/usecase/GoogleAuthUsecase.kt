package com.example.myecomartapp.domain.usecase

import android.accounts.Account
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import javax.inject.Inject

class GoogleAuthUsecase @Inject constructor( val authRepository: AuthRepository) {
    suspend  operator  fun  invoke(account: GoogleSignInAccount) : Result<String>{
        return authRepository.signInWithGoogle(account)
    }

}