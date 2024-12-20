package com.example.navigationapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import com.example.navigationapp.R

@Composable
fun ScreenLayout(
    title: String,
    showBottomBar: Boolean = false,
    showBackArrow: Boolean = true,
    navController: NavHostController,
    bottomBarButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(30.dp),
        bottomBar = {
            if(showBottomBar) {
                Surface(
                    modifier = Modifier.padding(20.dp),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        bottomBarButton!!()
                    }
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if(showBackArrow) {
                        Image(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = "Cofnij",
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            }
                        )
                    }
                    Text(
                        title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 5.em
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                content()
            }
        }
    }

}