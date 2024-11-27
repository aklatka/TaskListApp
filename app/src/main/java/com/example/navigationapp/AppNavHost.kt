package com.example.navigationapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.navigationapp.screens.NavigationItem
import com.example.navigationapp.screens.NewTaskScreen
import com.example.navigationapp.screens.TaskListScreen
import com.example.navigationapp.screens.TaskScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.TaskList.route,
) {
    val tasksViewModel: TasksViewModel = viewModel()

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.TaskList.route) {
            TaskListScreen(modifier, navController, tasksViewModel)
        }
        composable(NavigationItem.NewTask.route) {
            NewTaskScreen(modifier, navController, tasksViewModel)
        }
        composable(
            "${NavigationItem.Task.route}/{taskId}",
            arguments = listOf(navArgument("taskId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")

            if(taskId == null) {
                navController.navigate(NavigationItem.TaskList.route)
            } else {
                TaskScreen(modifier, navController, tasksViewModel, taskId)
            }
        }
    }
}