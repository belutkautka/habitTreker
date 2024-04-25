package com.application.hw2.db

import androidx.room.*;

import com.application.hw2.model.HabitModel;

@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(habit:HabitModel)

    @Query("SELECT * from HabitModel ORDER BY id DESC")
    fun selectAllHabits(): List<HabitModel>

    @Query("SELECT * from HabitModel WHERE type = 0 ORDER BY id DESC")
    fun selectBadHabits(): List<HabitModel>

    @Query("SELECT * FROM HabitModel WHERE type = 1 ORDER BY id DESC")
    fun selectGoodHabits(): List<HabitModel>
    @Query("SELECT * FROM HabitModel WHERE name LIKE '%' || :searchName || '%' ORDER BY id DESC")
    fun searchHabits(searchName:String): List<HabitModel>

    @Delete
    fun deleteHabit(habit: HabitModel)

    @Update
    fun updateHabit(habit: HabitModel)

}
