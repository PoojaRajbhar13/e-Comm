package com.example.myecomartapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myecomartapp.domain.model.CartState
import com.example.myecomartapp.domain.remote.Product
import com.example.myecomartapp.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                cartRepository.getCartItems().collect { items ->
                    val total = items.sumOf { it.product.price?.times(it.quantity) ?: 0.0 }
                    val totalItem = items.sumOf { it.quantity }
                    _state.value = _state.value.copy(
                        cartItems = items,
                        isLoading = false,
                        totalPrice = total.toString(),
                        totalItem = totalItem,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Fail to load cart items"
                )
            }
        }
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            try {
                cartRepository.addToCart(product, quantity)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Fail to add to cart"
                )
            }
        }
    }

    fun removeFromCart(productId: Int?) {
        viewModelScope.launch {
            try {
                cartRepository.removeFromCart(productId)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Fail to remove from cart"
                )
            }
        }
    }

    fun updateQuantity(productId: Int?, quantity: Int) {
        viewModelScope.launch {
            try {
                cartRepository.updateQuantity(productId, quantity)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Fail to update quantity"
                )
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                cartRepository.clearCart()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Fail to clear cart"
                )
            }
        }
    }
}
