package com.example.myecomartapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferenceDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("UserPrefrence") //create preference file
        private  val IS_FIRST_TIME_LOGIN  = booleanPreferencesKey("is_first_time_login")/*false*/
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")/*true*/
    }

    val isFirstTimeLogin : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_FIRST_TIME_LOGIN] ?: true   // if null then bydefalt get true

    }

    val isLoggedIn : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }


    suspend fun  setFirstTimeLogin(isFirstTimeLogin: Boolean /*false*/){
        context.dataStore.edit {preferences ->
            preferences[IS_FIRST_TIME_LOGIN] = isFirstTimeLogin /*false*/
        }
    }

    suspend fun  setLoggedIn(isLoggedIn: Boolean /*true*/){
        context.dataStore.edit{prefrences ->
            prefrences[IS_LOGGED_IN] = isLoggedIn /*true*/
        }
    }

}