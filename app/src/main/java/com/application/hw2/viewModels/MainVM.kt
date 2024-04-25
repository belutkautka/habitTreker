package com.application.hw2.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.hw2.db.HabitsList
import com.application.hw2.enums.HabitType
import com.application.hw2.model.HabitModel

class MainVM : ViewModel() {
    private val _habits = MutableLiveData<List<HabitModel>>()

    val habits: LiveData<List<HabitModel>> = _habits

    init {
        _habits.value = HabitsList.habits
    }

    fun addHabit(newHabit: HabitModel) {
        HabitsList.insertToEnd(newHabit)
        _habits.value = HabitsList.habits
    }

    fun updateHabit(oldHabit: HabitModel, newHabit: HabitModel) {
        HabitsList.updateHabit(oldHabit, newHabit)
        _habits.value = HabitsList.habits
        Log.d("sort",_habits.value.toString())
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
            _habits.value = HabitsList.habits.filter { habit ->  habit.name.contains(name)}.toList()
    }
}