package com.example.myecomartapp.presentation.componentes

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myecomartapp.R
import com.example.myecomartapp.presentation.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlin.contracts.contract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun AuthLogo(authViewModel: AuthViewModel) {
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    val googleSignInLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->

            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        if (account != null) {
                            authViewModel.googleAuth(account)
                            Toast.makeText(context, "SignIn successful", Toast.LENGTH_SHORT).show()
                        } else {
                            errorMessage = "Google SignIn fail"
                        }
                    } catch (e: ApiException) {
                        errorMessage = " Google SignIn Fail"
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }

                Activity.RESULT_CANCELED -> {
                    Toast.makeText(context, "SignIn canceled", Toast.LENGTH_SHORT).show()
                    errorMessage = "Google SignIn canceled"

                }
            }

        }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = {}) {
            Image(
                painter = painterResource(id = R.drawable.applelogo),
                contentDescription = "Apple logo",
                modifier = Modifier.size(40.dp)

            )
        }
        IconButton(onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id)).build()
            val googleSignInClint = GoogleSignIn.getClient(context, gso)
            val sigInIntent = googleSignInClint.signInIntent
            googleSignInLauncher.launch(sigInIntent)
        }) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier.size(40.dp)
            )
        }

        IconButton(onClick = {}) {
            Image(
                painter = painterResource(R.drawable.facebook),
                contentDescription = "facebook",
                modifier = Modifier.size(40.dp)
            )
        }

    }
}
