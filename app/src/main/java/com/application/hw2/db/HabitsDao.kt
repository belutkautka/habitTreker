package com.application.hw2.db

import androidx.lifecycle.LiveData
import androidx.room.*;

import com.application.hw2.model.HabitModel;

@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit:HabitModel)

    @Query("SELECT * from HabitModel ORDER BY id DESC")
    fun selectAllHabits(): LiveData<List<HabitModel>>

    @Delete
    suspend fun deleteHabit(habit: HabitModel)

    @Update
    suspend fun updateHabit(habit: HabitModel)
}
