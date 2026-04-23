package com.example.myecomartapp.data.repositoryimple

import com.example.myecomartapp.data.local.CartDataStore
import com.example.myecomartapp.data.model.CartItem
import com.example.myecomartapp.domain.remote.Product
import com.example.myecomartapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImple @Inject constructor(private  val  cartDataStore: CartDataStore)  : CartRepository {

    override suspend fun addToCart(product: Product, quantity: Int) {
        cartDataStore.addToCart(product, quantity)
    }

    override suspend fun getCartItemCount(): Int {
        return cartDataStore.getCartItemCount()
    }

    override suspend fun clearCart() {
        cartDataStore.clearCart()

    }

    override suspend fun removeFromCart(productId: Int?) {
        cartDataStore.removeFromCart(productId)
    }

    override suspend fun updateQuantity(productId: Int?, quantity: Int) {
        cartDataStore.updateQuantity(productId, quantity)
    }

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDataStore.cartItem

    }


}