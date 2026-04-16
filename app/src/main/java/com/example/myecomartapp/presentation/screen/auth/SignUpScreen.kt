package com.example.myecomartapp.presentation.screen.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myecomartapp.core.util.Result
import com.example.myecomartapp.presentation.componentes.AuthLogo
import com.example.myecomartapp.presentation.navigation.Route
import com.example.myecomartapp.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(signupViewModel: AuthViewModel, navHostController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState by signupViewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is Result.Success -> {
                Toast.makeText(context, "SignUp Successfully", Toast.LENGTH_SHORT).show()
                navHostController.navigate(Route.HomeScreen) {
                    popUpTo(Route.SignUp) { inclusive = true }
                    popUpTo(Route.Login) { inclusive = true }
                }
            }
            is Result.Failure -> {
                Toast.makeText(context, (authState as Result.Failure).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ){
        Spacer(modifier = Modifier.height(40.dp))

        //=====heading========
        Text(
            text = "Create a \n new Account",
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
            onValueChange = {email = it},
            label = {Text("Email or user name ") },
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
            onValueChange = {password = it},
            label =  {Text("password")},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock,
                    contentDescription = "Password icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            label =  {Text("confirm password")},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock,
                    contentDescription = "Password icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (password == confirmPassword) {
                signupViewModel.signup(email = email, password = password)
            } else {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Sign up")
        }

        Spacer(modifier = Modifier.height(20.dp))

        AuthLogo(signupViewModel)

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text(text = "Already have an account? ")
            Text(
                text = "Login",
                color = Color.Red,
                modifier = Modifier.clickable {
                    navHostController.navigate(Route.Login) {
                        popUpTo(Route.SignUp) { inclusive = true }
                    }
                }
            )
        }
    }
}
