package com.application.hw2.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.domain.useCases.HabitUseCases
import com.application.domain.useCases.model.HabitModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: HabitUseCases,
    application: Application) : AndroidViewModel(application) {

    private val _habits: MutableLiveData<MutableList<HabitModel>> = MutableLiveData()
    val habits: LiveData<MutableList<HabitModel>> get() = _habits

    init {
        viewModelScope.launch {
            repository.init()
            _habits.postValue(repository.getAllHabitsFromDb().toMutableList())
        }
    }

    fun sortByPriority(desc: Boolean) {
        viewModelScope.launch() {
            _habits.postValue(repository.getHabitsSortedByPriority(desc).toMutableList())
        }
    }

    fun searchHabits(name: String) {
        viewModelScope.launch {
            _habits.postValue(repository.getHabitsFilteredByName(name).toMutableList())
        }
    }
}
