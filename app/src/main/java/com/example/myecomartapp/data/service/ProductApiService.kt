package com.example.myecomartapp.data.service

import com.example.myecomartapp.domain.remote.ProductDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class ProductApiService @Inject constructor (val httpClient: HttpClient){

    suspend fun getProducts(limit:Int = 0): ProductDto{
        return httpClient.get("products"){
            parameter("limit", limit)
        }.body()  //thumbnail data and title data


    }

    suspend fun searchProduct(query: String): ProductDto{
        return httpClient.get("products/search"){
            parameter("q", query)
        }.body()
    }
}



//https://dummyjson.com/products/search