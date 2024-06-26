package com.application.hw2.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.application.hw2.db.AppDatabase
import com.application.hw2.model.HabitModel
import com.application.hw2.repository.HabitRepository
import kotlinx.coroutines.launch

class FormViewModel(application: Application) : AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getHabitsDatabase(getApplication())
    private val habitsDao = appDatabase.habitsDao()
    private val repository: HabitRepository = HabitRepository(habitsDao)

    fun insertHabit(habit: HabitModel, new: Boolean = true) {
        viewModelScope.launch {
            repository.insert(habit, new)
        }
    }

    fun deleteHabit(habit: HabitModel){
        viewModelScope.launch {
            repository.delete(habit)
        }
    }

    fun habitDone(habit: HabitModel){
        viewModelScope.launch {
            repository.habitDone(habit)
        }
    }
}