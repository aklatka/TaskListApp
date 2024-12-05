package com.example.firebaseapp.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FormCard(
    errorMessage: String?,
    content: @Composable () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            content()
        }
        if(errorMessage != null) {
            Text(errorMessage, color = Color.Red)
        }
    }

}