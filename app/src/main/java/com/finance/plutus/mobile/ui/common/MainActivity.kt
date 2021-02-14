package com.finance.plutus.mobile.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainToolbar)
        setupNavigation()
    }

    private fun setupNavigation() {
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.dashboard,
            R.id.services,
            R.id.partners,
            R.id.invoices,
            R.id.transactions
        ).build()
        navController = Navigation.findNavController(this, R.id.main_container)
        NavigationUI.setupWithNavController(binding.mainBottomNv, navController)
        NavigationUI.setupWithNavController(
            binding.mainToolbar,
            navController,
            appBarConfiguration
        )
    }

}