//package com.application.hw2.db
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.application.hw2.model.HabitModel
//import com.application.hw2.model.LongListTypeConverter
//
//@Database(entities = [HabitModel::class], version = AppDatabase.VERSION)
//@TypeConverters(LongListTypeConverter::class)
//abstract class AppDatabase : RoomDatabase() {
//
//    abstract fun habitsDao(): HabitsDao
//
//    companion object {
//        const val DB_NAME = "habits.db"
//        const val VERSION: Int = 1
//
//        private var INSTANCE: AppDatabase? = null
//
//        fun getHabitsDatabase(context: Context): AppDatabase {
//            if (INSTANCE == null) {
//                synchronized(AppDatabase::class) {
//                    INSTANCE = Room
//                        .databaseBuilder(
//                            context.applicationContext,
//                            AppDatabase::class.java,
//                            DB_NAME
//                        )
//                        .build()
//                }
//            }
//            return INSTANCE!!
//        }
//    }
//}