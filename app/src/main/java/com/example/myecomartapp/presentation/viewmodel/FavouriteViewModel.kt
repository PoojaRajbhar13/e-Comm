package com.example.myecomartapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myecomartapp.domain.model.FavouriteState
import com.example.myecomartapp.domain.remote.Product
import com.example.myecomartapp.domain.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteViewModel @Inject constructor(private val favouriteRepository: FavouriteRepository): ViewModel(){

    private val _state = MutableStateFlow(FavouriteState())
    val state = _state.asStateFlow()

    init {
        loadAllFavProducts()
    }

    private fun loadAllFavProducts(){
         viewModelScope.launch {
             _state.value = _state.value.copy(isLoading = true)
             try{
                 favouriteRepository.getFavouriteProduct().collect {  product ->
                     _state.value = _state.value.copy(allProduct = product,
                         filteredProduct = product,
                         isLoading = false, error = null)

                 }
             }catch(e: Exception){
                 _state.value = _state.value.copy(isLoading = false, error = e.message?: "Fail to load favourite product")

             }
         }
    }

    fun addFavouriteProduct(product: Product){
        viewModelScope.launch {
            try{
                favouriteRepository.addFavouriteProduct(product )
            }catch(e: Exception){
                _state.value = _state.value.copy(error = e.message ?: "Fail to add favourite product")

            }

            }
        }


    fun removeFavouriteProduct(id: Int? ) {
      viewModelScope.launch{
          try{
              favouriteRepository.removeFavouriteProduct(id)
          }catch(e: Exception){

              _state.value = _state.value.copy(error = e.message ?: "Fail to remove favourite product")

          }

      }
    }




}