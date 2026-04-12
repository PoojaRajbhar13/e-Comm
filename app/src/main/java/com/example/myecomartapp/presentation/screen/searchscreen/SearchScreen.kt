package com.example.myecomartapp.presentation.screen.searchscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.presentation.common.LoadingIndicator
import com.example.myecomartapp.presentation.componentes.ProductCard
import com.example.myecomartapp.presentation.screen.homescreen.HomeScreen
import com.example.myecomartapp.presentation.viewmodel.ProductViewModel

@Composable
fun SearchScreen(navController: NavController, searchViewModel: ProductViewModel) {
    val searchState by searchViewModel.searchProduct.collectAsState()
    val searchQuery by searchViewModel.searchQuery.collectAsState()


     LaunchedEffect(Unit ) {
         searchViewModel.reset()
     }
    HomeScreen(
        navController = navController,
        home = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Professional Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchViewModel.updateSearchQuery(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    placeholder = { Text("Search for products...", color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.LightGray,
                    )
                )

                // Search Results Area
                Box(modifier = Modifier.fillMaxSize()) {
                    when (val state = searchState) {
                        is Result.Idle -> {
                            SearchPlaceholder(
                                title = "Discover Something New",
                                subtitle = "Search for your favorite gadgets, clothes, and more."
                            )
                        }

                        is Result.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingIndicator()
                            }
                        }

                        is Result.Success -> {
                            val filteredProducts = state.data.products.filter {
                                it.category !in listOf(
                                    "home-decoration", "kitchen-accessories", "motorcycle",
                                    "sports-accessories", "vehicle", "furniture", "groceries"
                                )
                            }

                            if (filteredProducts.isEmpty()) {
                                SearchPlaceholder(
                                    title = "No results found",
                                    subtitle = "Try adjusting your search query."
                                )
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(bottom = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(filteredProducts) { product ->
                                        ProductCard(
                                            modifier = Modifier.fillMaxWidth(),
                                            thumbnail = product.thumbnail,
                                            title = product.title,
                                            navController
                                        )
                                    }
                                }
                            }
                        }

                        is Result.Failure -> {
                            SearchPlaceholder(
                                title = "Oops! Something went wrong",
                                subtitle = state.message
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun SearchPlaceholder(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 20.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
