package com.example.todotodo.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @NonNull
    var date: String,
    @NonNull
    var contents: String,
    @NonNull
    var posted: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
