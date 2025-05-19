package com.example.myapplication_new.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication_new.MainApplication
import com.example.myapplication_new.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.time.Instant

class TodoViewModel : ViewModel() {

    private val todoDao = MainApplication.todoDatabase.getTodoDao()

    val todoList: LiveData<List<Todo>> = todoDao.getAllTodo()

    fun addTodo(title: String, description: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insert(Todo(title = title, description = description, createdAt = Date.from(Instant.now())))
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.delete(todo.id)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.update(todo)
        }
    }

    fun toggleCompletion(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.update(todo.copy(isCompleted = !todo.isCompleted))
        }
    }
}