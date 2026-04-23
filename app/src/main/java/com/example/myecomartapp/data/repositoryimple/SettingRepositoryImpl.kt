package com.example.myecomartapp.data.repositoryimple

import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.model.SettingProfileState
import com.example.myecomartapp.domain.repository.SettingRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class SettingRepositoryImpl @Inject constructor(val database: FirebaseDatabase) : SettingRepository {

    val userProfileRef = database.getReference("users")
    override suspend fun saveProfile(userProfile: SettingProfileState): Result<Unit> {
        return try {
            if (userProfile.userId.isEmpty()) {
                return Result.Failure("User Id is empty")
            }

            val profileMap = hashMapOf<String, Any>(
                "name" to userProfile.name,
                "userId" to userProfile.userId,
                "email" to userProfile.email,
                "password" to userProfile.password,
                "pincode" to userProfile.pincode,
                "address" to userProfile.address,
                "city" to userProfile.city,
                "state" to userProfile.state,
                "country" to userProfile.country,
                "bankAccountNumber" to userProfile.bankAccountNumber,
                "accountHolderName" to userProfile.accountHolderName,
                "ifscCode" to userProfile.ifscCode
            )
            userProfileRef.child(userProfile.userId).setValue(profileMap).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getProfile(userId: String): Flow<Result<SettingProfileState>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val userProfile = snapshot.getValue(SettingProfileState::class.java)
                    if (userProfile != null) {
                        trySend(Result.Success(userProfile))
                    } else {
                        trySend(Result.Success(SettingProfileState(userId = userId)))
                    }
                } catch (e: Exception) {
                    trySend(Result.Failure(e.message ?: "Unknown error occurred"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.Failure(error.message))
            }
        }

        userProfileRef.child(userId).addValueEventListener(listener)

        awaitClose {
            userProfileRef.child(userId).removeEventListener(listener)
        }
    }

    override suspend fun updateProfile(userId: String, updates: Map<String, Any>): Result<Unit> {
     return try{
         userProfileRef.child(userId).updateChildren(updates).await()
         Result.Success(Unit)
     }catch (e: Exception){
         Result.Failure(e.message ?: "Unknown error occurred")
     }
    }


}
