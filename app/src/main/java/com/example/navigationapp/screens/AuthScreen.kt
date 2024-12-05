package com.example.navigationapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import com.example.firebaseapp.controls.FormCard
import com.example.firebaseapp.controls.PasswordInput
import com.example.firebaseapp.controls.TextInput
import com.example.navigationapp.R
import com.example.navigationapp.controls.PrimaryButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var valid by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLogin by remember { mutableStateOf(true) }

    fun submit() {
        if (!valid) return;

        (if(isLogin)
            auth.signInWithEmailAndPassword(email, password)
            else auth.createUserWithEmailAndPassword(email, password)
        )
            .addOnSuccessListener {
                navController.navigate(NavigationItem.TaskList.route)
            }
            .addOnFailureListener { error ->
            errorMessage = error.localizedMessage
        }
    }

    fun validate() {
        valid = email.isNotEmpty() and
                password.isNotEmpty() and
                (isLogin or (
                    passwordConfirm == password
                ))
    }

    AuthLayout(
        topContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_task_launcher_foreground),
                    contentDescription = ""
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    "Logowanie",
                    fontWeight = if(isLogin) FontWeight.SemiBold else FontWeight.Light,
                    fontSize = if(isLogin) 5.em else 4.em,
                    color = if(isLogin) Color.Black else Color.Gray,
                    modifier = Modifier.clickable {
                        isLogin = true
                    }
                )
                Text(
                    "Rejestracja",
                    fontWeight = if(!isLogin) FontWeight.SemiBold else FontWeight.Light,
                    fontSize = if(!isLogin) 5.em else 4.em,
                    color = if(!isLogin) Color.Black else Color.Gray,
                    modifier = Modifier.clickable {
                        isLogin = false
                    }
                )
            }
        },
        bottomContent = {
           PrimaryButton(
               if(isLogin) "Zaloguj Się" else "Utwórz konto",
               disabled = !valid,
               onClick = {submit()}
           )
        }
    ) {

        FormCard(
            errorMessage = errorMessage
        ) {
            TextInput(
                value = email,
                onValueChange = {
                    email = it
                    validate()
                },
                label = "Email"
            )
            PasswordInput(
                value = password,
                onValueChange = {
                    password = it
                    validate()
                },
                label = "Hasło",
            )
            if(!isLogin) {
                PasswordInput(
                    value = passwordConfirm,
                    onValueChange = {
                        passwordConfirm = it
                        validate()
                    },
                    label = "Potwierdź Hasło",
                    errorMessage = if((passwordConfirm != password) and  passwordConfirm.isNotEmpty())
                        "Hasła nie są identyczne"
                        else null
                )
            }
        }

    }

}