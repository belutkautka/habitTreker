package com.application.hw2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.application.hw2.adapter.HabitAdapter
import com.application.hw2.databinding.ActivityMainBinding
import com.application.hw2.db.HabitsList
import com.application.hw2.enums.Keys
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
    }

    private fun initial() {
        recyclerView = binding.recyclerView
        adapter = HabitAdapter { habit: HabitModel, position: Int ->
            Intent(this, FormActivity::class.java).run {
                putExtra(Keys.HABIT_POSITION.name, position)
                putExtra(Keys.HABIT_TO_CHANGE.name, habit)
            }
        }
        recyclerView.adapter = adapter
        adapter.submitList(HabitsList.selectAllHabits())
        val fab: FloatingActionButton = findViewById(R.id.fabButton)
        fab.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        if (HabitsList.changed){
            adapter.submitList(HabitsList.selectAllHabits())
        }
        super.onResume()
    }
}
