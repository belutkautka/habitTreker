package com.application.hw2.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.application.hw2.db.AppDatabase
import com.application.hw2.db.HabitRepository
import com.application.hw2.model.HabitModel

class FilterVM(application: Application) : AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getHabitsDatabase(getApplication())
    private val habitsDao = appDatabase.habitsDao()
    private val repository: HabitRepository = HabitRepository(habitsDao)

    private val _habits = MediatorLiveData<List<HabitModel>>().apply {
        addSource(repository.allHabits) { value = it }
    }
    val habits: LiveData<List<HabitModel>> = _habits

    fun sortByPriority(desc: Boolean) {
        if (_habits.value!!.isEmpty())
            return
        if (!desc) {
            val sortedList = repository.allHabits.value?.sortedBy { it.name }

            _habits.value = sortedList
        } else {
            val sortedList = repository.allHabits.value?.sortedByDescending { it.name }
            _habits.value = sortedList
        }
    }

    fun searchHabits(name: String) {
        val filteredList =
            repository.allHabits.value?.filter { it.name.contains(name, ignoreCase = true) }
        _habits.value = filteredList
    }
}