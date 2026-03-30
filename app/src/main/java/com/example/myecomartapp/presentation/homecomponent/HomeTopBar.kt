package com.example.myecomartapp.presentation.homecomponent

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myecomartapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onListClick: () -> Unit, onProfileClick: () -> Unit) {

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
               Icon(
                   imageVector = Icons.Default.AccountCircle,
                   contentDescription = "Profile",
                   modifier = Modifier.size(32.dp)
               )
           }
        }
    )
}