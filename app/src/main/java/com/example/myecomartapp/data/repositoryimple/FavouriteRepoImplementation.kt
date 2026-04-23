package com.example.myecomartapp.data.repositoryimple

import com.example.myecomartapp.data.local.dao.CommonDao
import com.example.myecomartapp.domain.remote.Product
import com.example.myecomartapp.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteRepoImplementation @Inject constructor(private val commonDao: CommonDao) : FavouriteRepository {

    override suspend fun addFavouriteProduct(product: Product) {
      return commonDao.insertFavouriteProduct(product)
    }

    override fun getFavouriteProduct(): Flow<List<Product>> {
       return commonDao.getAllFavouriteProduct()
    }

    override suspend fun removeFavouriteProduct(id: Int?) {
        return commonDao.deleteFavouriteProduct(id)
    }

    override suspend fun isFavourite(id: Int): Boolean {
        return commonDao.isFavourite(id)
    }


    override suspend fun clearFavouriteProduct() {
     return commonDao.clearFavouriteProduct()
    }


}

