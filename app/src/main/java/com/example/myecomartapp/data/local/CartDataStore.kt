package com.example.myecomartapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myecomartapp.data.model.CartItem
import com.example.myecomartapp.domain.remote.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class CartDataStore(private val context: Context)
{
    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("Cart_Preferences")
        private val CART_ITEMS = stringPreferencesKey("cart_items")

    }

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true

    }
    val cartItem : Flow<List<CartItem>> = context.dataStore.data.map { preferences ->
        val itemsJson = preferences[CART_ITEMS] ?: "[]"
         try{
             json.decodeFromString<List<CartItem>>(itemsJson)
         }catch (e: Exception){
             emptyList()
         }

    }

    suspend fun addToCart(product: Product, quantity: Int = 1){
        context.dataStore.edit { preferences ->
            val currentJson =  preferences[CART_ITEMS] ?: "[]"
            val currentItems = try {
                json.decodeFromString<List<CartItem>>(currentJson)
            } catch (e: Exception) {
                emptyList()
            }

            val existingItemIndex = currentItems.indexOfFirst { it.product.id == product.id }
            val updatedItems = if (existingItemIndex != -1) {
                currentItems.toMutableList().apply {
                    val existingItem = this[existingItemIndex]
                    this[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + quantity)
                }
            } else {
                currentItems + CartItem(product, quantity)
            }
            
            preferences[CART_ITEMS] = json.encodeToString(updatedItems)
        }
    }


    suspend fun removeFromCart(productId: Int?){

        context.dataStore.edit { preferences ->
            val currentJson = preferences[CART_ITEMS] ?: "[]"
            val currentItems =  try{
                json.decodeFromString<List<CartItem>>(currentJson)
            }catch (e: Exception){
                emptyList()

            }
            val updatedItems = currentItems.filter { it.product.id != productId}
            preferences[CART_ITEMS] = json.encodeToString(updatedItems)
        }

    }

    suspend fun updateQuantity(productId: Int?, quantity: Int){
        context.dataStore.edit { preferences ->
            val currentJson = preferences[CART_ITEMS] ?: "[]"
            val currentItems = try {
                json.decodeFromString<List<CartItem>>(currentJson)
            } catch (e: Exception) {
                emptyList()
            }

            val updatedItems = currentItems.map { item ->
                if(item.product.id == productId){
                    item.copy(quantity = quantity)
                }else {
                    item
                }
            }.filter { it.quantity > 0 }

            preferences[CART_ITEMS] = json.encodeToString(updatedItems)
        }

    }

    suspend fun clearCart(){
        context.dataStore.edit { preferences ->
            preferences[CART_ITEMS] = "[]"
        }

    }

    suspend fun getCartItemCount(): Int {
        val preferences = context.dataStore.data.first()
        val currentJson = preferences[CART_ITEMS] ?: "[]"
        val currentItems = try {
            json.decodeFromString<List<CartItem>>(currentJson)
        } catch (e: Exception) {
            emptyList()
        }
        return currentItems.sumOf { it.quantity }
    }
}
