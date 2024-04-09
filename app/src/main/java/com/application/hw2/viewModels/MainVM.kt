package com.application.hw2.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.application.hw2.db.HabitsList
import com.application.hw2.enums.HabitType
import com.application.hw2.model.HabitModel

class MainVM : ViewModel() {
    val habits: LiveData<MutableList<HabitModel>> = HabitsList.habits

    fun addHabit(newHabit: HabitModel) {
        HabitsList.insertToEnd(newHabit)
    }

    fun updateHabit(oldHabit: HabitModel, newHabit: HabitModel) {
        HabitsList.updateHabit(oldHabit, newHabit)
    }

    fun selectHabitsByType(type: HabitType): List<HabitModel> {
        return HabitsList.selectHabitsByType(type)
    }
}