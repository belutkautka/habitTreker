package com.application.hw2.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hw2.db.AppDatabase
import com.application.hw2.db.HabitRepository
import com.application.hw2.model.HabitModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterVM(application: Application) : AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getHabitsDatabase(getApplication())
    private val habitsDao = appDatabase.habitsDao()
    private val repository: HabitRepository = HabitRepository(habitsDao)

//    private var _habits = MutableLiveData<List<HabitModel>>()

    lateinit var habits: LiveData<List<HabitModel>>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            habits = repository.allHabits
        }
    }

    fun sortByPriority(desc: Boolean) {
        if (habits.value!!.isEmpty())
            return
        if (!desc) {
            val sortedList = repository.allHabits.value?.sortedBy { it.name }

            habits = MutableLiveData(sortedList)
        } else {
            val sortedList = repository.allHabits.value?.sortedByDescending { it.name }
            habits = MutableLiveData(sortedList)
        }
    }

    fun searchHabits(name: String) {
        val filteredList = repository.allHabits.value?.filter { it.name.contains(name, ignoreCase = true) }
        habits = MutableLiveData(filteredList)
    }
}