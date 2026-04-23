package com.example.myecomartapp.presentation.componentes

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.myecomartapp.domain.remote.Product
import com.example.myecomartapp.presentation.viewmodel.FavouriteViewModel

@Composable
fun  FavouriteIconComponent(favouriteViewModel: FavouriteViewModel, product: Product) {

    val context = LocalContext.current
    val state by favouriteViewModel.state.collectAsState()
    
    // Check by ID instead of object equality
    val isFavourite = state.allProduct.any { it.id == product.id }

    IconButton(
        onClick = {
            if(isFavourite){
                favouriteViewModel.removeFavouriteProduct(product.id)
                Toast.makeText(context, "Removed from favourites", Toast.LENGTH_SHORT).show()
            }else{
                favouriteViewModel.addFavouriteProduct(product)
                Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
            }
        }
    ){
        Icon(
            imageVector = if(!isFavourite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
            contentDescription = null,
            modifier = Modifier,
            tint = if(!isFavourite) Color.Gray else Color.Red,
        )
    }

}