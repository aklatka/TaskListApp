package com.example.navigationapp.screens

enum class Screen {
    AUTH,
    NEW_TASK,
    TASK_LIST,
    TASK
}

sealed class NavigationItem(val route: String) {
    data object NewTask : NavigationItem(Screen.NEW_TASK.name)
    data object TaskList : NavigationItem(Screen.TASK_LIST.name)
    data object Task : NavigationItem(Screen.TASK.name)
    data object Auth : NavigationItem(Screen.AUTH.name)
}
