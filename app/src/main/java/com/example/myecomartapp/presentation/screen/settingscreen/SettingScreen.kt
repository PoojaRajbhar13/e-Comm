package com.example.myecomartapp.presentation.screen.settingscreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.myecomartapp.domain.model.SettingProfileState
import com.example.myecomartapp.presentation.common.LoadingIndicator
import com.example.myecomartapp.presentation.componentes.SaveButton
import com.example.myecomartapp.presentation.componentes.SettingsTextField
import com.example.myecomartapp.presentation.viewmodel.SettingProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController, settingProfileViewModel: SettingProfileViewModel) {

    val pinkColor = Color(0xFFF73B5B) // Pinkish-red color from the UI
    val context = LocalContext.current
    val settingState by settingProfileViewModel.state.collectAsState()
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var bankAccountNumber by remember { mutableStateOf("") }
    var accountHolderName by remember { mutableStateOf("") }
    var ifscCode by remember { mutableStateOf("") }



    LaunchedEffect(settingState.userProfile.email) {
        if (settingState.userProfile.email.isNotBlank()) {
        }
    }

    LaunchedEffect(settingState.saveSuccess) {
        if (settingState.saveSuccess) {
            if (name.isNotBlank() && pincode.isNotBlank() && address.isNotBlank()
                && city.isNotBlank() && state.isNotBlank() && country.isNotBlank()
                && bankAccountNumber.isNotBlank() && accountHolderName.isNotBlank()
                && ifscCode.isNotBlank()) {
                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                settingProfileViewModel.resetSaveSuccess()
            } else{
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()

            }
        }
    }

    LaunchedEffect(settingState.error) {
        settingState.error?.let {
            if (it.isNotBlank()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
            settingProfileViewModel.clearError()
        }
    }

    LaunchedEffect(Unit) {
        settingProfileViewModel.loadUserData()
        settingProfileViewModel.loadUserProfile()

    }

    LaunchedEffect(settingState.userProfile) {

        name = settingState.userProfile.name

        password = settingState.userProfile.password

        email = settingState.userProfile.email

        pincode = settingState.userProfile.pincode

        address = settingState.userProfile.address

        city = settingState.userProfile.city

        state = settingState.userProfile.state

        country = settingState.userProfile.country

        bankAccountNumber = settingState.userProfile.bankAccountNumber

        accountHolderName = settingState.userProfile.accountHolderName
        ifscCode = settingState.userProfile.ifscCode


    }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Setting",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        },


        containerColor = Color.White
    ) { paddingValues ->
        when {
            settingState.isLoading -> {
                LoadingIndicator()
            }
            else ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    // Profile Image Section
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        // Placeholder for profile image
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color(0xFFE57373)), // Placeholder background
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                               model = settingState.profileUrl,
                                contentDescription = "Profile Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )
                        }

                        // Edit Icon
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .align(Alignment.BottomEnd)
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(Color(0xFF4285F4))
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Profile",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Personal Details Section
                    Text(
                        text = "Personal Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    SettingsTextField(
                        label = "Name",
                        value = name,
                        onValueChange = { name = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    SettingsTextField(
                        label = "Email Address",
                        value = email,
                        onValueChange = { email = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SettingsTextField(
                        label = "Password",
                        value = password,
                        onValueChange = { password = it },

                        )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Change Password",
                        color = pinkColor,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = Color(0xFFF0F0F0), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    // Business Address Details Section
                    Text(
                        text = "Business Address Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    SettingsTextField(
                        label = "Pincode",
                        value = pincode,
                        onValueChange = { pincode = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SettingsTextField(
                        label = "Address",
                        value = address,
                        onValueChange = { address = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SettingsTextField(
                        label = "City",
                        value = city,
                        onValueChange = { city = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SettingsTextField(
                        label = "State",
                        value = state,
                        onValueChange = { state = it },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SettingsTextField(
                        label = "Country",
                        value = country,
                        onValueChange = { country = it }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = Color(0xFFF0F0F0), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    // Bank Account Details Section
                    Text(
                        text = "Bank Account Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    SettingsTextField(
                        label = "Bank Account Number",
                        value = bankAccountNumber,
                        onValueChange = { bankAccountNumber = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SettingsTextField(
                        label = "Account Holder's Name",
                        value = accountHolderName,
                        onValueChange = { accountHolderName = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SettingsTextField(
                        label = "IFSC Code",
                        value = ifscCode,
                        onValueChange = { ifscCode = it }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    SaveButton(settingProfileViewModel, onClick = {

                        val allFilled = name.isNotBlank() && pincode.isNotBlank() && address.isNotBlank()
                                && city.isNotBlank() && state.isNotBlank() && country.isNotBlank()
                                && password.isNotBlank() && bankAccountNumber.isNotBlank()
                                && accountHolderName.isNotBlank() && ifscCode.isNotBlank()

                        val anyChange = name != settingState.userProfile.name || pincode != settingState.userProfile.pincode
                                || address != settingState.userProfile.address || city != settingState.userProfile.city
                                || state != settingState.userProfile.state || country != settingState.userProfile.country
                                || password != settingState.userProfile.password || bankAccountNumber != settingState.userProfile.bankAccountNumber
                                || accountHolderName != settingState.userProfile.accountHolderName || ifscCode != settingState.userProfile.ifscCode


                        if(!allFilled){
                            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                            return@SaveButton
                        }

                        if(!anyChange){
                            Toast.makeText(context, "No changes made", Toast.LENGTH_SHORT).show()
                            return@SaveButton

                        }

                        val userProfile = SettingProfileState(
                            email = when(val account = GoogleSignIn.getLastSignedInAccount(context)){
                                null -> settingProfileViewModel.firebaseAuth.currentUser ?.email ?: ""
                                else -> account.email ?: "Already logged in with Google"
                            } ,
                            name = name,
                            password = password,
                            pincode = pincode,
                            address = address,
                            city = city,
                            state = state,
                            country = country,
                            bankAccountNumber = bankAccountNumber,
                            accountHolderName = accountHolderName,
                            ifscCode = ifscCode

                        )
                        settingProfileViewModel.updateUserProfile(userProfile)




                    })

                    Spacer(modifier = Modifier.height(32.dp))
                }
        }
    }
}
