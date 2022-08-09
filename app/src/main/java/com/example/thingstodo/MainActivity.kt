package com.example.thingstodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.thingstodo.fragments.ThingToDoFragment
import com.example.thingstodo.fragments.ToDoFragmentDirections
import com.example.thingstodo.other.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        //TWO WAYS OF CHANGING APP THEME
        // AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        // setTheme(R.style.Theme_ThingsToDo_Dark)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initialFragment = intent.getStringExtra(Constants.INITIAL_FRAGMENT)
        println("This fragment " + initialFragment)
        println(intent.extras)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        initialFragment?.let {
            if(it == ThingToDoFragment.TAG){
                val thingToDoid = this@MainActivity.intent.getIntExtra(ThingToDoFragment.ID, -1)
                if(thingToDoid >= 0) {
                    val action = ToDoFragmentDirections.actionToDoFragmentToThingToDoFragment(id = thingToDoid)
                    navController.navigate(action)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}