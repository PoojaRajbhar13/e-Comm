package com.example.myecomartapp.domain.repository

import com.example.myecomartapp.data.model.CartItem
import com.example.myecomartapp.domain.remote.Product
import kotlinx.coroutines.flow.Flow


interface CartRepository {

    fun  getCartItems(): Flow<List<CartItem>>
    suspend fun  addToCart(product: Product , quantity: Int = 1)
    suspend fun removeFromCart(productId: Int?)
    suspend fun updateQuantity(productId: Int?, quantity: Int)
    suspend fun clearCart()
    suspend fun getCartItemCount(): Int

}