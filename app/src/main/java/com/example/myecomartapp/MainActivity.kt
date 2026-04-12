package com.example.myecomartapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myecomartapp.presentation.navigation.AppNavigation
import com.example.myecomartapp.presentation.screen.auth.SignUpScreen
import com.example.myecomartapp.presentation.screen.product.ProductDetailScreen
import com.example.myecomartapp.presentation.viewmodel.AuthViewModel
import com.example.myecomartapp.ui.theme.MyEcomartAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyEcomartAppTheme {
                AppNavigation()
                }
            }
        }
    }
