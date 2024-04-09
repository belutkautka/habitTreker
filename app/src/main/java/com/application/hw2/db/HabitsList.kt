package com.application.hw2.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.hw2.enums.HabitType
import com.application.hw2.model.HabitModel

// Это симуляция бд
object HabitsList {
    private val _habits = MutableLiveData<MutableList<HabitModel>>()

    // LiveData для наблюдения за изменениями
    val habits: LiveData<MutableList<HabitModel>> = _habits

    init {
        _habits.value = mutableListOf()
    }

    fun selectHabitsByType(type: HabitType): List<HabitModel> {
        return _habits.value!!.filter { habit -> habit.type == type }
    }

    fun updateHabit(oldHabit: HabitModel, newHabit: HabitModel) {
        val index = _habits.value!!.indexOf(oldHabit)
        val updatedHabits = _habits.value!!.toMutableList()
        updatedHabits.removeAt(index)
        updatedHabits.add(newHabit)
        _habits.value = updatedHabits
    }

    fun insertToEnd(habit: HabitModel) {
        val updatedHabits = _habits.value!!.toMutableList()
        updatedHabits.add(habit)
        _habits.value = updatedHabits
    }
}