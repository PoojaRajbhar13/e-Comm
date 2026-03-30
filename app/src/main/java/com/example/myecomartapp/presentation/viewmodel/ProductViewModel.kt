package com.example.myecomartapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.domain.remote.ProductDto
import com.example.myecomartapp.domain.repository.ProductRepository
import com.example.myecomartapp.domain.usecase.productusecase.ProductUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUsecase: ProductUsecase,
    val searchRepository: ProductRepository
) : ViewModel() {
    private val _allProducts = MutableStateFlow<Result<ProductDto>>(Result.Idle)
    private val _searchProduct = MutableStateFlow<Result<ProductDto>>(Result.Idle)

    val allProducts = _allProducts.asStateFlow()
    val searchProduct = _searchProduct.asStateFlow()


    //    init{
//        getAllProducts()
//    }
    fun getAllProducts() {

        viewModelScope.launch(Dispatchers.IO) {

            _allProducts.value = Result.Loading

            val result = productUsecase()  // thumbnail data and title data
            _allProducts.value = result

        }


    }


    fun searchProduct(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            _searchProduct.value = Result.Loading
            val result =  searchRepository.searchProduct(query)
            _searchProduct.value = result
        }
        
    }
}