package com.example.myecomartapp.presentation.screen.wishlist


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myecomartapp.presentation.common.LoadingIndicator
import com.example.myecomartapp.presentation.componentes.ProductCard
import com.example.myecomartapp.presentation.screen.homescreen.HomeScreen
import com.example.myecomartapp.presentation.viewmodel.FavouriteViewModel
import com.example.myecomartapp.presentation.viewmodel.SettingProfileViewModel


@Composable
fun  WishListScreen(navController: NavController, settingProfileViewModel: SettingProfileViewModel, favouriteViewModel: FavouriteViewModel){

    val state by favouriteViewModel.state.collectAsState()

 HomeScreen(
     settingProfileViewModel = settingProfileViewModel,
     navController = navController,
     home = {
         Column(modifier = Modifier.fillMaxSize()) {
             Text( text = "WishList",
                 fontWeight = Bold,
                 fontSize = 30.sp,
                 color = Color.Black,
                 fontStyle = FontStyle.Italic,
                 modifier = Modifier.padding(15.dp)
                     .align(Alignment.CenterHorizontally)

             )
             Divider(modifier = Modifier.fillMaxWidth())
             when{
                 state.isLoading -> {
                     LoadingIndicator()
                 }
                 state.error != null -> {
                     Box(
                         modifier = Modifier.fillMaxSize(),
                         contentAlignment = Alignment.Center

                     ){
                         Text(text = "Please connect to the internet", fontSize = 30.sp)
                     }
                 }

                 state.filteredProduct.isEmpty() && state.allProduct.isEmpty() ->{
                     Box(
                         modifier = Modifier.fillMaxSize(),
                         contentAlignment = Alignment.Center
                     ){
                         Text(text = "No Favourite Product", fontSize = 30.sp)

                     }
                 } else ->{
                 LazyVerticalGrid(columns = GridCells.Fixed(2),
                     modifier = Modifier.padding(8.dp)) {
                     items(state.filteredProduct){ product ->
                         ProductCard(
                             modifier = Modifier.width(160.dp)
                                 .padding(8.dp),
                             product = product,
                             navController = navController,
                             favouriteViewModel = favouriteViewModel
                         )

                     }
                 }
                 }

             }


         }
     }
 )
}