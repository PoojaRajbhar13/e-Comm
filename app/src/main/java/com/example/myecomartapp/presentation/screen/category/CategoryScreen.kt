package com.example.myecomartapp.presentation.screen.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.presentation.common.LoadingIndicator
import com.example.myecomartapp.presentation.componentes.ProductCard
import com.example.myecomartapp.presentation.viewmodel.FavouriteViewModel
import com.example.myecomartapp.presentation.viewmodel.ProductViewModel

@Composable
fun CategoryScreen(
    categoryName: String,
    navController: NavController,
    productViewModel: ProductViewModel,
    favouriteViewModel: FavouriteViewModel
) {

    val state by  productViewModel.allProducts.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(
            text = "Showing result for : ${categoryName.replaceFirstChar { it.uppercase() }}",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 16.dp)
        )

        when(val currentState = state){
            is Result.Loading -> {
                LoadingIndicator()
            }
            is Result.Success ->{
                val filteredProducts = currentState.data.products.filter { product ->
                    val productCategory = product.category?.lowercase()
                    val  clickName = categoryName.lowercase()


                    val allowedCategories = when(clickName){
                        "mens" -> listOf("mens-shirts", "mens-shoes", "mens-watches", "mens-perfume")
                        "women's" ->listOf("women's-dresses", "women's-shoes", "women's-bags", "women's-jewellery")
                        "beauty" -> listOf("beauty", "skincare", "fragrances", "cloths")
                        "fashion" -> listOf("mens-shirts", "women's-dresses", "mens-shoes", "women's-shoes")
                        "kids" -> listOf("toys", "kids-clothes")
                        else -> listOf(clickName)
                    }
                    allowedCategories.contains(productCategory)
                }

                if(filteredProducts.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
                        Text(text = "No product found in the category.")
                    }
                } else{

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)

                    ) {
                        items(filteredProducts) { product ->

                            ProductCard(modifier = Modifier.fillMaxWidth(),
                                product = product,
                                navController = navController,
                                favouriteViewModel = favouriteViewModel
                            )

                        }
                    }
                }
            }

            is Result.Failure ->{
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = currentState.message)
                }
            }
            else -> {}

        }
    }

}