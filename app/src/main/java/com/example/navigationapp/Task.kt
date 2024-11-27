package com.example.navigationapp

enum class TaskPriority(val iconRes: Int, val displayName: String) {
    LOW_PRIORITY(R.drawable.low_priority, "Niski"),
    MEDIUM_PRIORITY(R.drawable.medium_priority, "Średni"),
    HIGH_PRIORITY(R.drawable.high_priority, "Wysoki"),

}

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    var done: Boolean = false
)
