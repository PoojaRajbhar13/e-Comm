package com.example.myecomartapp.domain.repository

import com.example.myecomartapp.core.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<String>  // suspend   non blocking asynchronous function

    suspend fun signup(email: String, password: String): Result<String>

    suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<String>
}