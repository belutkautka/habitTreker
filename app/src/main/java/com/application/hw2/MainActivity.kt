package com.application.hw2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.application.hw2.adapter.HabitAdapter
import com.application.hw2.com.application.hw2.MainFragment
import com.application.hw2.com.application.hw2.MainFragment.Companion.FRAGMENT_TAG
import com.application.hw2.databinding.ActivityMainBinding
import com.application.hw2.db.HabitsList
import com.application.hw2.enums.Keys
import com.application.hw2.model.HabitModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val fragment = MainFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainFrameLayout, fragment, FRAGMENT_TAG)
                .commit()
        }
    }

}