package com.example.myecomartapp.domain.usecase.productusecase

import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.remote.ProductDto
import com.example.myecomartapp.domain.repository.ProductRepository
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String /*shoes*/): Result<ProductDto> {
        if (query.isBlank()) {
            return Result.Idle
        }
        return repository.searchProduct(query) // shoes
    }
}
