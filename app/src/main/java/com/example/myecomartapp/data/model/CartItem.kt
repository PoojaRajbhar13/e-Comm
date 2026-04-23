package com.example.myecomartapp.data.model

import com.example.myecomartapp.domain.remote.Product
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val product: Product,
    val quantity: Int = 1
)