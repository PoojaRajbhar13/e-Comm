package com.example.myecomartapp.presentation.screen.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myecomartapp.R
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.data.model.Category
import com.example.myecomartapp.presentation.common.LoadingIndicator
import com.example.myecomartapp.presentation.componentes.ProductCard
import com.example.myecomartapp.presentation.homecomponent.Banner
import com.example.myecomartapp.presentation.homecomponent.CategorySection
import com.example.myecomartapp.presentation.navigation.Route
import com.example.myecomartapp.presentation.viewmodel.FavouriteViewModel
import com.example.myecomartapp.presentation.viewmodel.ProductViewModel
import com.example.myecomartapp.presentation.viewmodel.SettingProfileViewModel

@Composable
fun HomePage(
    navController: NavController,
    productViewModel: ProductViewModel,
    settingProfileViewModel: SettingProfileViewModel,
    favouriteViewModel: FavouriteViewModel
) {
    val state by productViewModel.allProducts.collectAsState()

    //list
    val categoryList = listOf(
        Category(1,"Beauty", R.drawable.beauty),
        Category(2, "Fashion", R.drawable.fashion),
        Category(3, "Kids", R.drawable.kids),
        Category(4, "Mens", R.drawable.men),
        Category(5, "Women's", R.drawable.women)
    )


    HomeScreen(
        settingProfileViewModel = settingProfileViewModel,
        navController = navController,
        home = {
            when (val state = state) {
                is Result.Loading -> {
                    LoadingIndicator()
                }
                is Result.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

//category
                        item(span = { GridItemSpan(2)}){
                            CategorySection(
                                categories =  categoryList,
                                onCategoryClick = {categoryId  ->
                                    val clickedCategory = categoryList.find { it.id == categoryId}?.name ?: "Unknown"
                                    navController.navigate(Route.CategoryList(categoryName = clickedCategory))
                                }

                            )

                        }


                        //banner
                        item(span = { GridItemSpan(2) }) {
                            androidx.compose.foundation.layout.Box(
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Banner(
                                    imageRes = R.drawable.banner,
                                    onBannerClick = {
                                    }
                                )
                            }
                        }






                        // Featured Products Section
                        item(span = { GridItemSpan(2) }) {
                            Text(
                                text = "Featured Products",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(bottom = 16.dp)
                            ) {
                                items(state.data.products.filter {
                                    it.category != "home-decoration"
                                            && it.category != "kitchen-accessories"
                                            && it.category != "motorcycle"
                                            && it.category != "sports-accessories"
                                            && it.category != "vehicle"
                                            && it.category != "furniture"
                                            && it.category != "groceries"
                                }.take(15).shuffled()) { product ->
                                    ProductCard(
                                        modifier = Modifier.width(160.dp),
                                        product = product,
                                        navController = navController,
                                        favouriteViewModel = favouriteViewModel
                                    )
                                }
                            }
                        }

                        // All Products Section
                        item(span = { GridItemSpan(2) }) {
                            Text(
                                text = "All Products",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(state.data.products.filter {
                            it.category != "home-decoration"
                                    && it.category != "kitchen-accessories"
                                    && it.category != "motorcycle"
                                    && it.category != "sports-accessories"
                                    && it.category != "vehicle"
                                    && it.category != "furniture"
                                    && it.category != "groceries"
                        }) { product ->
                            ProductCard(
                                modifier = Modifier.fillMaxWidth(),
                                product = product,
                                navController = navController,
                                favouriteViewModel = favouriteViewModel
                            )
                        }
                    }
                }
                is Result.Failure -> {
                    Text(text = state.message)
                }
                else -> {}
            }
        }
    )
}
