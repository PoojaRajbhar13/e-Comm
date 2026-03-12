package com.example.myecomartapp.presentation.screen.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.presentation.navigation.Route
import com.example.myecomartapp.presentation.viewmodel.AuthViewModel


@Composable
fun LoginScreen(loginViewModel: AuthViewModel, navHostController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by loginViewModel.authState.collectAsState()
    val context = LocalContext.current
    var error by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        when (authState) {
            is Result.Idle -> {
                error = ""
            }

            is Result.Loading -> {
                // You can add a loading indicator here if you want
            }

            is Result.Success -> {
                Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
                // Navigate to HomeScreen and clear the backstack
                navHostController.navigate(Route.HomeScreen) {
                    popUpTo(Route.Login) { inclusive = true }
                }
            }


            is Result.Failure -> {
                error = (authState as Result.Failure).message
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        Spacer(modifier = Modifier.height(40.dp))

        //=====heading========
        Text(
            text = "Welcome \n Back",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 40.dp)
                .wrapContentWidth(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(40.dp))

        //Email/username
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email or user name ") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        //====password text field

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                loginViewModel.login(email = email, password = password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "Login"

            )
        }


    }


}
