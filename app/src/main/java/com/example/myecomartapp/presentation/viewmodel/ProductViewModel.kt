package com.example.myecomartapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.remote.ProductDto
import com.example.myecomartapp.domain.usecase.productusecase.ProductUsecase
import com.example.myecomartapp.domain.usecase.productusecase.SearchProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUsecase: ProductUsecase,
    private val searchProductUseCase: SearchProductUseCase
) : ViewModel() {
    private val _allProducts = MutableStateFlow<Result<ProductDto>>(Result.Idle)
    private val _searchProduct = MutableStateFlow<Result<ProductDto>>(Result.Idle) //result data
    private val _searchQuery = MutableStateFlow("")

    val allProducts = _allProducts.asStateFlow()
    val searchProduct = _searchProduct.asStateFlow()
    val searchQuery = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    init {
        getAllProducts()
    }

    fun reset(){
        _searchProduct.value = Result.Idle
        _searchQuery.value = ""
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        searchProduct(query)
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _allProducts.value = Result.Loading
            val result = productUsecase()
            _allProducts.value = result
        }
    }

    fun searchProduct(query: String)/*shoes*/ {
        searchJob?.cancel()
        if (query.isBlank()) {
            _searchProduct.value = Result.Idle
            return
        }

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(500L) // Debounce
            _searchProduct.value = Result.Loading
            val result = searchProductUseCase(query) //shoes
            _searchProduct.value = result
        }
    }
}
