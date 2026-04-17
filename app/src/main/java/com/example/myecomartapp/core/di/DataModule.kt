package com.example.myecomartapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.myecomartapp.data.local.UserPreferenceDataStore
import com.example.myecomartapp.data.local.dao.CommonDao
import com.example.myecomartapp.data.local.database.Database
import com.example.myecomartapp.data.repositoryimple.AuthRepositoryImp
import com.example.myecomartapp.data.repositoryimple.ProductRepoImplementation
import com.example.myecomartapp.data.repositoryimple.SettingRepositoryImpl
import com.example.myecomartapp.data.repositoryimple.UserPreferenceRepoImplementation
import com.example.myecomartapp.data.service.ProductApiService
import com.example.myecomartapp.domain.repository.AuthRepository
import com.example.myecomartapp.domain.repository.ProductRepository
import com.example.myecomartapp.domain.repository.SettingRepository
import com.example.myecomartapp.domain.repository.UserPreferenceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.cio.CIOMultipartDataBase
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImp(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideUserPreferenceDataStore(@ApplicationContext context: Context): UserPreferenceDataStore {
        return UserPreferenceDataStore(context)
    }

    @Provides
    @Singleton
    fun provideUserPrefrenceRepository(userPrefrenceDataStore: UserPreferenceDataStore): UserPreferenceRepository {
        return UserPreferenceRepoImplementation(userPrefrenceDataStore)
    }

    @Provides
    @Singleton
    fun provideHttpClint(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS //https://
                    host = "dummyjson.com" //dummyjson.com/
                }

            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun provideProductApiService(httpClient: HttpClient): ProductApiService {
        return ProductApiService(httpClient)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productApiService: ProductApiService): ProductRepository {
        return ProductRepoImplementation(productApiService)
    }


    @Provides
    @Singleton
    fun provideFirebaseDatabase() : FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideSettingProfileRepo(dataBase: FirebaseDatabase): SettingRepository{
        return SettingRepositoryImpl(dataBase)
    }


    @Provides
    @Singleton
    fun provideFavouritesDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(context,  klass = Database :: class.java, name = "Favourite_Database").build()
    }


    @Provides
    @Singleton
    fun provideCommonDao(database: Database): CommonDao{
        return database.favouriteDao


    }




}


