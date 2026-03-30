package com.example.myecomartapp.domain.usecase.productusecase

import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.remote.ProductDto
import com.example.myecomartapp.domain.repository.ProductRepository
import javax.inject.Inject

class ProductUsecase @Inject constructor(val repository: ProductRepository) {

    suspend operator fun invoke(): Result<ProductDto>{
        return repository.getAllProduct()  //thumbnail data and title data
    }

}