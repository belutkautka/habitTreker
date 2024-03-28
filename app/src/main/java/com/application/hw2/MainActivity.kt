package com.application.hw2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.application.hw2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy {
        findNavController(R.id.mainFrameLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbarMainActivity = binding.toolbarMainActivity
        setSupportActionBar(toolbarMainActivity)

        val appBarConfiguration =
            AppBarConfiguration(navController.graph, binding.navigationDrawerLayout)

        toolbarMainActivity.setupWithNavController(navController, appBarConfiguration)

        binding.navigationView.setupWithNavController(navController)

        if (savedInstanceState == null) {
            navController.setGraph(R.navigation.navigation_graph)
        }
//        if (savedInstanceState == null) {
//            val fragment = MainFragment.newInstance()
//
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.mainFrameLayout, fragment, FRAGMENT_TAG)
//                .commit()
//        }
    }

}