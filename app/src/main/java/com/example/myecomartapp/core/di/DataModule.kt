package com.example.myecomartapp.core.di

import android.content.Context
import androidx.compose.ui.tooling.preview.Preview
import com.example.myecomartapp.data.local.UserPreferenceDataStore

import com.example.myecomartapp.data.repositoryimple.AuthRepositoryImp
import com.example.myecomartapp.data.repositoryimple.UserPreferenceRepoImplementation
import com.example.myecomartapp.domain.repository.AuthRepository
import com.example.myecomartapp.domain.repository.UserPreferenceRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{  //firebaseAuth return type
        return FirebaseAuth.getInstance()  // to access firebase functions

    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth) : AuthRepository{
        return AuthRepositoryImp(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideUserPreferenceDataStore(@ApplicationContext context: Context) : UserPreferenceDataStore{
        return UserPreferenceDataStore(context)
    }


    @Provides
    @Singleton
    fun provideUserPrefrenceRepository(userPrefrenceDataStore: UserPreferenceDataStore): UserPreferenceRepository{
        return UserPreferenceRepoImplementation(userPrefrenceDataStore)
    }
}