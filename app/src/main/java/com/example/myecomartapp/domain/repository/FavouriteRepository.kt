package com.example.myecomartapp.domain.repository


import com.example.myecomartapp.domain.remote.Product
import kotlinx.coroutines.flow.Flow


interface FavouriteRepository {
    fun getFavouriteProduct(): Flow<List<Product>>
    suspend fun  addFavouriteProduct(product: Product)
    suspend fun removeFavouriteProduct(id: Int?)
    suspend fun  isFavourite(id: Int): Boolean
    suspend fun clearFavouriteProduct()


}