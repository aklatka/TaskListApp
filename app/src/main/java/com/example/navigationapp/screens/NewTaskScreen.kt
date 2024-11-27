package com.example.navigationapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.navigationapp.R
import com.example.navigationapp.TaskPriority
import com.example.navigationapp.TasksViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NewTaskScreen(modifier: Modifier, navController: NavHostController, tasksViewModel: TasksViewModel = viewModel()) {
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskPriority by remember { mutableStateOf(TaskPriority.LOW_PRIORITY) }
    var valid by remember { mutableStateOf(false) }

    fun validate() {
        valid = taskTitle.isNotBlank() && taskDescription.isNotBlank()
    }

    ScreenLayout(
        "Nowe Zadanie",
        showBottomBar = true,
        bottomBarButton = {
            Button(
                onClick = {
                    if(valid) {
                        tasksViewModel.addTask(
                            title = taskTitle,
                            description = taskDescription,
                            priority = taskPriority
                        )
                        navController.popBackStack()
                    }
                },
                colors = if(valid) ButtonDefaults.buttonColors()
                else ButtonDefaults.buttonColors(Color.LightGray)
            ) {
                Text("Zapisz")
            }
        },
        navController = navController
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = taskTitle,
                onValueChange = {taskTitle = it},
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Tytuł")
                }
            )
            TextField(
                onValueChange = {
                    taskDescription = it
                    validate()
                },
                value = taskDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                label = {
                    Text("Opis")
                }
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text("Priorytet", fontSize = 4.em)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    TaskPriority.entries.forEach {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            RadioButton(
                                selected = (it.name == taskPriority.name),
                                onClick = {
                                    taskPriority = it
                                    validate()
                                }
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(it.displayName)
                                Image(
                                    painter = painterResource(
                                        it.iconRes
                                    ),
                                    contentDescription = it.displayName
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}