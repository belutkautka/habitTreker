package com.application.hw2

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.application.hw2.adapter.HabitAdapter
import com.application.hw2.databinding.ActivityMainBinding
import com.application.hw2.model.HabitModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: HabitAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
    }

    private fun initial() {
        recyclerView = binding.recyclerView
        adapter = HabitAdapter()
        recyclerView.adapter = adapter
        adapter.habitList = habitsCreator()
        adapter.notifyDataSetChanged()
    }

    fun habitsCreator(): ArrayList<HabitModel>{
        val list = ArrayList<HabitModel>()
        val habit1 = HabitModel("Первая привычка", "Описание какое-то",
            "","спорт",1,"неделю")
        val habit2 = HabitModel("Вторая привычка", "Описание какое-тооооооо",
            "","дз",2,"месяц")
        val habit3 = HabitModel("Третья привычка", "К черту описание",
            "","дз",10,"год")
        list.add(habit1)
        list.add(habit2)
        list.add(habit3)
        return list
    }
}
