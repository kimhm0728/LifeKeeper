package com.example.todotodo.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todotodo.database.Todo
import com.example.todotodo.database.TodoDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    val allTodoList: LiveData<List<Todo>>?
    private val repository: TodoRepository?

    init {
        val todoDao = TodoDatabase.getInstance(application)?.todoDao()

        repository = TodoRepository(todoDao)
        allTodoList = repository.allTodoList
    }

    fun insert(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository?.insert(todo)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TodoViewModel(application) as T
        }
    }
}