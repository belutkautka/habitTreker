package com.application.hw2.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.application.hw2.db.AppDatabase
import com.application.hw2.db.HabitRepository
import com.application.hw2.model.HabitModel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getHabitsDatabase(getApplication())
    private val habitsDao = appDatabase.habitsDao()
    private val repository: HabitRepository = HabitRepository(habitsDao)

    private val _habits = MediatorLiveData<List<HabitModel>>().apply {
        addSource(repository.allHabits) { value = it }
    }
    val habits: LiveData<List<HabitModel>> = _habits

    init {
        viewModelScope.launch {
            repository.initFromApi()
        }
    }

    fun sortByPriority(desc: Boolean) {
        viewModelScope.launch {
            _habits.value = repository.getHabitsSortedByPriority(desc)
        }
    }

    fun searchHabits(name: String) {
        viewModelScope.launch {
            _habits.value = repository.getHabitsFilteredByName(name)
        }
    }
}