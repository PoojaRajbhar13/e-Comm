package com.example.myecomartapp.domain.usecase

import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUsecase @Inject constructor(val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<String>{  // invoke fun = class and function run simultaneously
        return authRepository.login(email,password)
    }
}