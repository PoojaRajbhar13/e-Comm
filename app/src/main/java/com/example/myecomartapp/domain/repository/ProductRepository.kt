package com.example.myecomartapp.domain.repository

import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.remote.ProductDto

interface ProductRepository{

    suspend fun getAllProduct(): Result<ProductDto>  // thumbnail data product data

    suspend fun searchProduct(query: String): Result<ProductDto>

}