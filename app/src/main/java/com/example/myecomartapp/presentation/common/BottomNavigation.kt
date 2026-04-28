package com.example.myecomartapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myecomartapp.R
import com.example.myecomartapp.presentation.navigation.Route

sealed class BottomNavItem(
    val route: Any,
    val title: String,
    val icon: Int
) {
    data object Home : BottomNavItem(Route.HomeScreen, "Home", R.drawable.home)
    data object Wishlist : BottomNavItem(Route.Wishlist, "Wishlist", R.drawable.heart)
    data object Cart : BottomNavItem(Route.Cart, "Cart", R.drawable.cart)
    data object Search : BottomNavItem(Route.SearchScreen, "Search", R.drawable.search)
    data object Settings : BottomNavItem(Route.Settings, "Setting", R.drawable.settings)
}

@Composable
fun BottomNavigationBar(
    currentRoute: Any?, // Not strictly needed as we use navController internally
    onItemClick: (BottomNavItem) -> Unit,
    navController: NavController
) {
    val leftItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Wishlist
    )

    val rightItems = listOf(
        BottomNavItem.Search,
        BottomNavItem.Settings
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Bottom navigation bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.Pink))
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left items
            leftItems.forEach { item ->
                val isSelected = currentDestination?.hasRoute(item.route::class) == true
                BottomNavItemView(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onItemClick(item) }
                )
            }

            // Empty space for floating cart button
            Box(modifier = Modifier.size(60.dp))

            // Right items
            rightItems.forEach { item ->
                val isSelected = currentDestination?.hasRoute(item.route::class) == true
                BottomNavItemView(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onItemClick(item) }
                )
            }
        }

        // Floating cart button
        val isCartSelected = currentDestination?.hasRoute(Route.Cart::class) == true
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp)
                .size(56.dp)
                .shadow(8.dp, CircleShape)
                .background(Color.White, CircleShape)
                .clickable { onItemClick(BottomNavItem.Cart) }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Cart",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(
                    color = if (isCartSelected) colorResource(R.color.Crimson) else Color.Black
                )
            )
        }
    }
}

@Composable
fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val selectedColor = colorResource(R.color.Crimson)
    val unselectedColor = Color.Black
    
    val contentColor = if (isSelected) selectedColor else unselectedColor
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                onClick = { onClick() },
                indication = null,
                interactionSource = interactionSource
            )
            .padding(horizontal = 12.dp)
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(color = contentColor)
        )

        Text(
            text = item.title,
            fontSize = 12.sp,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
