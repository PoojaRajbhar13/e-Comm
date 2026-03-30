package com.example.myecomartapp.data.repositoryimple

import android.R
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.data.service.ProductApiService
import com.example.myecomartapp.domain.remote.ProductDto
import com.example.myecomartapp.domain.repository.ProductRepository
import io.ktor.client.HttpClient
import javax.inject.Inject


class ProductRepoImplementation @Inject constructor( private val productApiService: ProductApiService /*thumbnail data and title data*/): ProductRepository {

    override suspend fun getAllProduct(): Result<ProductDto> {
        return try{
            val response : ProductDto = productApiService.getProducts() //thumbnail data and title data
            Result.Success(response)
        }catch (e: Exception){
            Result.Failure(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun searchProduct(query: String): Result<ProductDto> {
        return try{
            val  search : ProductDto = productApiService.searchProduct(query)
            Result.Success(search)
        }catch (e: Exception){
            Result.Failure(e.localizedMessage ?: " unknown error")
        }
    }


}