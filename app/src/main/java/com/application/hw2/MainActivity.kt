package com.application.hw2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.application.hw2.fragments.MainFragment
import com.application.hw2.fragments.MainFragment.Companion.FRAGMENT_TAG
import com.application.hw2.databinding.ActivityMainBinding

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