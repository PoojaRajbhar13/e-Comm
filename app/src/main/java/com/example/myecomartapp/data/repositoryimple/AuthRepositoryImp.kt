package com.example.myecomartapp.data.repositoryimple

import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.InstallIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor( private  val firebaseAuth: FirebaseAuth): AuthRepository{
    override suspend fun login(email: String, password: String): Result<String> {
        return try{
            firebaseAuth.signInWithEmailAndPassword(email, password).await() // await() used for delay
            Result.Success("Login Successfully")
        }catch (e: Exception){
            Result.Failure(e.message ?: "unknown error")
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): Result<String> {
        return try{
            firebaseAuth.createUserWithEmailAndPassword(email, password).await() // await() used for delay
            Result.Success("SignUp Successfully")
        }catch (e: Exception){
            Result.Failure(e.message ?: "unknown error")
        }
    }

}