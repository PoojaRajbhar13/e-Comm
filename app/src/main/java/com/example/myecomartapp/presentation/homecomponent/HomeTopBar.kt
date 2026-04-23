package com.example.myecomartapp.presentation.homecomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.myecomartapp.R
import com.example.myecomartapp.presentation.viewmodel.SettingProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    settingProfileViewModel: SettingProfileViewModel,
    onListClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCartClick: () -> Unit = {}
) {

    val state by settingProfileViewModel.state.collectAsState()

    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(R.drawable.applogo),
                contentDescription = "StylishLogo",
                modifier = Modifier.size(120.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = onListClick) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "List",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        actions = {

            IconButton(onClick = onProfileClick) {
                AsyncImage(
                    model = state.profileUrl ?: R.drawable.google,
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp)
                        .clip(CircleShape)
                )
            }
        }
    )
}
