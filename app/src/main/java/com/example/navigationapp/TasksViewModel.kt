package com.example.navigationapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class TasksViewModel : ViewModel() {

    private val _tasks = MutableStateFlow(emptyList<Task>())
    val tasks: StateFlow<List<Task>> get() = _tasks

    fun getTask(id: Int): Task {
        return _tasks.value.first { it.id == id }
    }

    fun addTask(title: String, description: String, priority: TaskPriority) {
        _tasks.update { currTasks ->
            val task = Task(_tasks.value.size, title, description, priority)
            currTasks + task
        }
    }

    fun updateTaskStatus(id: Int, done: Boolean) {
        _tasks.update { currTasks ->
            currTasks.map { if(it.id == id) it.copy(done = done) else it }
        }
    }

}