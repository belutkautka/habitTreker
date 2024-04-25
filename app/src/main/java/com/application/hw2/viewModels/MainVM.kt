package com.application.hw2.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.hw2.db.AppDatabase
import com.application.hw2.model.HabitModel

class MainVM(application: Application) : AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getHabitsDatabase(getApplication())
    private val habitsDao = appDatabase.habitsDao()

    private val _habits = MutableLiveData<List<HabitModel>>()

    val habits: LiveData<List<HabitModel>> = _habits

    init {
        _habits.value = habitsDao.selectAllHabits().toMutableList()
    }

    fun addHabit(habit: HabitModel) {
        habitsDao.insertHabit(habit)
        _habits.value = habitsDao.selectAllHabits().toMutableList()
    }

    fun updateHabit(habit: HabitModel) {
        habitsDao.updateHabit(habit)
        _habits.value = habitsDao.selectAllHabits().toMutableList()
    }

    fun sortByPriority(desc: Boolean) {
        if (habits.value!!.isEmpty())
            return
        if (!desc) {
            val sortedHabits = _habits.value?.sortedBy { it.priority }
            _habits.value = sortedHabits?.toList()
        } else {
            val sortedHabits = _habits.value?.sortedByDescending { it.priority }
            _habits.value = sortedHabits?.toList()
        }
    }

    fun searchHabits(name: String) {
        _habits.value = habitsDao.searchHabits(name)
    }
}