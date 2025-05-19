package com.example.myapplication_new.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication_new.model.Todo

@Database(entities = [Todo::class], version = 3)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    companion object {
        const val NAME = "Todo_DB"
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Змінюємо, щоб description було nullable (без NOT NULL)
                db.execSQL("ALTER TABLE Todo ADD COLUMN description TEXT")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Видаляємо DEFAULT 0, щоб відповідати моделі
                db.execSQL("ALTER TABLE Todo ADD COLUMN isCompleted INTEGER NOT NULL")
            }
        }
    }

    abstract fun getTodoDao(): TodoDao
}