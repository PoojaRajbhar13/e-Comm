package com.example.myecomartapp.data.model

import com.example.myecomartapp.R

data class ProductCardData(
        val imageRes: Int = R.drawable.cardimage,
        val productName: String = "Women Printed Kurta",
        val description: String = "Neque porro quisquam est qui dolorem ipsum quia",
        val price: Int = 1500,
        val originalPrice: Int = 2499,
        val discountPercent: Int = 40,
        val rating: Float = 3.5f,
        val reviewCount: Int = 56890
    )

