package com.application.hw2.db

import androidx.lifecycle.LiveData
import androidx.room.*;

import com.application.hw2.model.HabitModel;

@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitModel)

    @Query("SELECT * from habit_table ORDER BY id DESC")
    fun getAllHabits(): LiveData<List<HabitModel>>

    @Delete
    suspend fun deleteHabit(habit: HabitModel)

    @Query(
        "SELECT * FROM habit_table ORDER BY "
                + "CASE WHEN :desc = 1 THEN priority END DESC,"
                + "CASE WHEN :desc = 0 THEN priority END ASC"
    )
    suspend fun getHabitsSortedByPriority(desc: Boolean): List<HabitModel>

    @Query("SELECT * FROM habit_table WHERE name LIKE '%' || :name || '%'")
    suspend fun getHabitsFilteredByName(name: String): List<HabitModel>
}
