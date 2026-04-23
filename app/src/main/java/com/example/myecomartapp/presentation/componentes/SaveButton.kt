package com.example.myecomartapp.presentation.componentes

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myecomartapp.presentation.common.LoadingIndicator
import com.example.myecomartapp.presentation.viewmodel.SettingProfileViewModel


@Composable
fun  SaveButton( settingProfileViewModel: SettingProfileViewModel, onClick: () -> Unit) {

    val state by settingProfileViewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.error) {
        if(state.error != null){
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            settingProfileViewModel.clearError()
        }
    }

    // Save Button
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        if(state.isSaving){
            LoadingIndicator()
        }else if (state.error != null){
            settingProfileViewModel.resetSaveSuccess()
        }else {
            Text(
                text = "Save",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp

            )
        }
    }


}