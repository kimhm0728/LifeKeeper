package com.example.todotodo.mvvm

import androidx.lifecycle.LiveData
import com.example.todotodo.database.Todo
import com.example.todotodo.database.TodoDao

class TodoRepository(private val todoDao: TodoDao?) {

    val allTodoList : LiveData<List<Todo>>? = todoDao?.selectAll()

    suspend fun insert(todo: Todo) {
        todoDao?.insert(todo)
    }
}