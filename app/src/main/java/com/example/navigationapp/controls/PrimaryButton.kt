package com.example.navigationapp.controls

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.navigationapp.R

@Composable
fun PrimaryButton(
    text: String,
    onClick: (() -> Unit)? = null,
    disabled: Boolean = false
) {

    Button(
        onClick = {if(!disabled) onClick!!()},
        colors = if(disabled) ButtonDefaults.buttonColors(Color.LightGray)
                else ButtonDefaults.buttonColors(
                    Color(ContextCompat.getColor(LocalContext.current,
                        R.color.ic_task_launcher_background))
                ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text, color = if(disabled) Color.Gray else Color.White)
    }
}