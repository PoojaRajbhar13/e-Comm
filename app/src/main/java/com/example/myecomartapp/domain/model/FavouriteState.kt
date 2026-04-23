package com.example.myecomartapp.domain.model

import com.example.myecomartapp.domain.remote.Product

data class FavouriteState(
    val allProduct: List<Product> = emptyList(),
    val  filteredProduct: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error : String? = null,
    val searchQuery: String = ""

)