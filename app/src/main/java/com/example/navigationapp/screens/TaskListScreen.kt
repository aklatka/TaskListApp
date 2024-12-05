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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.navigationapp.R
import com.example.navigationapp.Task
import com.example.navigationapp.TaskPriority
import com.example.navigationapp.TasksViewModel
import com.example.navigationapp.controls.PrimaryButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

@SuppressLint("MutableCollectionMutableState")
@Composable
fun TaskListScreen(modifier: Modifier, navController: NavHostController, tasksViewModel: TasksViewModel = viewModel()) {
    var tasks by remember { mutableStateOf(arrayListOf<Task>()) }
    val auth = FirebaseAuth.getInstance()
    val dbRef = Firebase.database.getReference("users/${auth.currentUser?.uid}/tasks")

    dbRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val tmpTasks = arrayListOf<Task>()
            for(snap in snapshot.children) {
                val data = snap.value as Map<*, *>

                tmpTasks.add(Task(
                    id = snap.key as String,
                    title = data["title"] as String,
                    description = data["description"] as String,
                    priority = TaskPriority.valueOf(data["priority"] as String),
                    done = (data["done"] ?: false) as Boolean
                ))
            }
            tasks = tmpTasks
        }

        override fun onCancelled(error: DatabaseError) {

        }
    })

    fun openTask(id: String) {
        navController.navigate("${NavigationItem.Task.route}/${id}")
    }

    ScreenLayout(
        "Lista Zadań",
        showBottomBar = true,
        showBackArrow = false,
        bottomBarButton = {
            PrimaryButton(
                "Dodaj + ",
                onClick = {navController.navigate(NavigationItem.NewTask.route)}
            )
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
                TaskItem(task, onClick = {openTask(task.id)})
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
                TaskItem(task, onClick = {openTask(task.id)})
            }
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TaskItem(task: Task, onClick: (id: String) -> Unit) {
    var done by mutableStateOf(task.done)
    val auth = FirebaseAuth.getInstance()
    val dbRef = Firebase.database.getReference("users/${auth.currentUser?.uid}/tasks/${task.id}")

    fun toggleDone() {
        done = !done
        dbRef.child("done").setValue(done)
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