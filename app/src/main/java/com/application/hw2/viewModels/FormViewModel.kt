package com.application.hw2.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.application.domain.useCases.HabitUseCases
import com.application.domain.useCases.model.HabitModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FormViewModel @Inject constructor(
    private val repository: HabitUseCases, application: Application) : AndroidViewModel(application) {

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