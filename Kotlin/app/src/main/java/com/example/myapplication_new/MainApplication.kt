package com.example.myapplication_new

import android.app.Application
import androidx.room.Room
import com.example.myapplication_new.db.TodoDatabase

class MainApplication : Application() {
    companion object {
        lateinit var todoDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.NAME
        ).addMigrations(TodoDatabase.MIGRATION_1_2, TodoDatabase.MIGRATION_2_3)
            .build()
    }
}