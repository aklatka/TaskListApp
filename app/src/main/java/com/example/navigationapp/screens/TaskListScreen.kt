package com.example.navigationapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.navigationapp.R
import com.example.navigationapp.Task
import com.example.navigationapp.TasksViewModel

@Composable
fun TaskListScreen(modifier: Modifier, navController: NavHostController, tasksViewModel: TasksViewModel = viewModel()) {
    val tasks by tasksViewModel.tasks.collectAsState()

    fun openTask(id: Int) {
        navController.navigate("${NavigationItem.Task.route}/${id}")
    }

    ScreenLayout(
        "Lista Zadań",
        showBottomBar = true,
        showBackArrow = false,
        bottomBarButton = {
            Button(
                onClick = {navController.navigate(NavigationItem.NewTask.route)}
            ) {
                Text("Dodaj +")
            }
        },
        navController = navController
    ) {
        Text(
            "Do zrobienia:",
            fontWeight = FontWeight.Normal,
            fontSize = 4.em
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            for(task in tasks.filter { t -> !t.done }) {
                TaskItem(task, tasksViewModel, onClick = {openTask(it)})
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            "Zakończone:",
            fontWeight = FontWeight.Normal,
            fontSize = 4.em
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            for(task in tasks.filter { t -> t.done }) {
                TaskItem(task, tasksViewModel, onClick = {openTask(it)})
            }
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TaskItem(task: Task, tasksViewModel: TasksViewModel = viewModel(), onClick: (id: Int) -> Unit) {
    var done by mutableStateOf(task.done)

    fun toggleDone() {
        done = !done
        tasksViewModel.updateTaskStatus(task.id, done)
    }

    Surface(modifier = Modifier.clickable {
        onClick(task.id)
    }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(done, onCheckedChange = {toggleDone()})
            Spacer(modifier = Modifier.width(3.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(task.title,
                    style = TextStyle(
                        textDecoration = if(task.done) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
                Image(
                    painter = painterResource(
                        if(task.done) R.drawable.done else task.priority.iconRes
                    ),
                    contentDescription = task.priority.name
                )
            }
        }
    }

}