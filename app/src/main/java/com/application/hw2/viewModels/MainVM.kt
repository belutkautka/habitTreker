package com.application.hw2.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.application.hw2.db.AppDatabase
import com.application.hw2.model.HabitModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainVM(application: Application) : AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getHabitsDatabase(getApplication())
    private val habitsDao = appDatabase.habitsDao()

    fun addHabit(habit: HabitModel) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsDao.insertHabit(habit)
        }
    }

    fun updateHabit(habit: HabitModel) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsDao.updateHabit(habit)
        }
    }
}