package com.example.myecomartapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myecomartapp.domain.remote.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface CommonDao {

    @Query("SELECT * FROM Favourite" )
    fun getAllFavouriteProduct(): Flow<List<Product>>

    @Query("SELECT * FROM Favourite  WHERE id =:id")
    suspend fun getFavouriteItem(id: Int): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertFavouriteProduct(product: Product)


    @Query("SELECT EXISTS (SELECT 1 FROM Favourite WHERE id = :id)")
    suspend fun isFavourite(id: Int): Boolean

    @Query("DELETE FROM Favourite WHERE id = :id")
    suspend fun deleteFavouriteProduct(id: Int?)

    @Query("SELECT COUNT (*) FROM  Favourite")
    suspend fun getFavouriteCount(): Int


    @Query("DELETE FROM Favourite")
    suspend fun clearFavouriteProduct()












}