package com.application.hw2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.application.hw2.adapter.HabitAdapter
import com.application.hw2.databinding.ActivityMainBinding
import com.application.hw2.model.HabitModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: HabitAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
        val fab: FloatingActionButton = findViewById(R.id.fabButton)
        fab.setOnClickListener {
            habits = adapter.habitList
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
        val createdHabit = intent.getSerializableExtra("HABIT_CREATE") as? HabitModel
        val changedHabit = intent.getSerializableExtra("HABIT_CHANGED") as? HabitModel
        if (createdHabit != null) {
            adapter.habitList.add(createdHabit)
            adapter.notifyDataSetChanged()
        } else if (changedHabit != null) {
            adapter.habitList[changedHabit.position] = changedHabit
            adapter.notifyItemChanged(changedHabit.position)
        }
    }

    private fun initial() {
        recyclerView = binding.recyclerView
        adapter = HabitAdapter(this)
        recyclerView.adapter = adapter
        adapter.habitList = habits
        adapter.notifyDataSetChanged()
    }

    companion object {
        var habits = ArrayList<HabitModel>()
    }
}
