package com.application.hw2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.application.hw2.databinding.ActivityMainBinding
import com.application.hw2.viewModels.MainVM

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    private lateinit var viewModel: MainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainFrameLayout) as NavHostFragment
        navController = navHostFragment.navController

        val toolbarMainActivity = binding.toolbarMainActivity
        setSupportActionBar(toolbarMainActivity)

        val appBarConfiguration =
            AppBarConfiguration(navController.graph, binding.navigationDrawerLayout)

        toolbarMainActivity.setupWithNavController(navController, appBarConfiguration)

        binding.navigationView.setupWithNavController(navController)

        if (savedInstanceState == null) {
            navController.setGraph(R.navigation.navigation_graph)
        }

        viewModel = ViewModelProvider(this).get(MainVM::class.java)
    }
}