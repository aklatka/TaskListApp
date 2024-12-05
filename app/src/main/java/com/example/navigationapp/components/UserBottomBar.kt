package com.example.navigationapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserBottomBar() {
    val auth = FirebaseAuth.getInstance()

    Surface() {
        Row {
            Image(imageVector = Icons.Filled.PersonPin, "user")

            if(auth.currentUser != null) {
                Text(auth.currentUser!!.email.toString())
            }
        }
    }
}