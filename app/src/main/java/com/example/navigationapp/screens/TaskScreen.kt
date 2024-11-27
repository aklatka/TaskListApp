package com.example.navigationapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import com.example.navigationapp.TasksViewModel

@Composable
fun TaskScreen(
    modifier: Modifier,
    navController: NavHostController,
    tasksViewModel: TasksViewModel,
    taskId: Int
) {
    val task by remember { mutableStateOf(tasksViewModel.getTask(taskId)) }

    ScreenLayout(
        "Szczegóły Zadania",
        showBottomBar = true,
        bottomBarButton = {
            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Cofnij")
            }
        },
        navController = navController
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("Tytuł", fontSize = 3.em, fontWeight = FontWeight.SemiBold)
            Text(task.title, fontSize = 4.em)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("Opis", fontSize = 3.em, fontWeight = FontWeight.SemiBold)
            Text(task.description, fontSize = 4.em)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("Priorytet", fontSize = 3.em, fontWeight = FontWeight.SemiBold)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(task.priority.displayName, fontSize = 4.em)
                Image(
                    painter = painterResource(
                        task.priority.iconRes
                    ),
                    contentDescription = task.priority.displayName
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("Status", fontSize = 3.em, fontWeight = FontWeight.SemiBold)
            Text(if(task.done) "Zakończone" else "Aktywne", fontSize = 4.em)
        }
    }

}