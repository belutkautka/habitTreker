package com.application.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.application.data.model.HabitModel
import com.application.data.model.LongListTypeConverter

@Database(entities = [HabitModel::class], version = AppDatabase.VERSION)
@TypeConverters(LongListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun habitsDao(): HabitsDao

    companion object {
        const val DB_NAME = "habits.db"
        const val VERSION = 1
    }
}