package com.example.myapplication_new.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication_new.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo ORDER BY createdAt DESC")
    fun getAllTodo(): LiveData<List<Todo>>

    @Insert
    fun insert(todo: Todo)

    @Query("DELETE FROM Todo WHERE id = :id")
    fun delete(id: Int)

    @Update
    fun update(todo: Todo)

    @Query("SELECT * FROM Todo WHERE id = :id")
    fun getTodoById(id: Int): LiveData<Todo?>
}