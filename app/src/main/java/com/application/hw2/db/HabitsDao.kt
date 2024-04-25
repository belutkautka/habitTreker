package com.application.hw2.db

import androidx.room.*;

import com.application.hw2.model.HabitModel;

@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit:HabitModel)

    @Query("SELECT * from HabitModel ORDER BY id DESC")
    suspend fun selectAllHabits(): List<HabitModel>

    @Query("SELECT * from HabitModel WHERE type = 0 ORDER BY id DESC")
    suspend fun selectBadHabits(): List<HabitModel>

    @Query("SELECT * FROM HabitModel WHERE type = 1 ORDER BY id DESC")
    suspend fun selectGoodHabits(): List<HabitModel>
    @Query("SELECT * FROM HabitModel WHERE name LIKE '%' || :searchName || '%' ORDER BY id DESC")
    suspend fun searchHabits(searchName:String): List<HabitModel>

    @Delete
    suspend fun deleteHabit(habit: HabitModel)

    @Update
    suspend fun updateHabit(habit: HabitModel)

}
