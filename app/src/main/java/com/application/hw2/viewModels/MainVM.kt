package com.application.hw2.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hw2.db.AppDatabase
import com.application.hw2.model.HabitModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainVM(application: Application) : AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getHabitsDatabase(getApplication())
    private val habitsDao = appDatabase.habitsDao()

    private val _habits = MutableLiveData<List<HabitModel>>()

    val habits: LiveData<List<HabitModel>> = _habits

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _habits.postValue(habitsDao.selectAllHabits().toMutableList())
        }
    }

    fun addHabit(habit: HabitModel) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsDao.insertHabit(habit)
        }
        viewModelScope.launch(Dispatchers.IO) {
            _habits.postValue(habitsDao.selectAllHabits().toMutableList())
        }
    }

    fun updateHabit(habit: HabitModel) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsDao.updateHabit(habit)
        }
        viewModelScope.launch(Dispatchers.IO) {
            _habits.postValue(habitsDao.selectAllHabits().toMutableList())
        }
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
        viewModelScope.launch(Dispatchers.Main) {
            _habits.value = habitsDao.searchHabits(name)
        }
    }
}