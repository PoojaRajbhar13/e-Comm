package com.example.myecomartapp.domain.model

import com.example.myecomartapp.data.model.CartItem

data class CartState(
    val cartItems: List<CartItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val totalPrice: String = "0.0",
    val totalItem : Int = 0
)