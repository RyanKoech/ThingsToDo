package com.example.thingstodo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thingstodo.MainActivity
import com.example.thingstodo.sharedpref.SharedPref
import com.example.thingstodo.SplashScreenActivity
import com.example.thingstodo.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    @Inject
    internal lateinit var sharedPref: SharedPref


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup? , savedInstanceState: Bundle?) : View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.toggleDarkmodeSwitch.apply {
            isChecked = sharedPref.loadNightModeState()!!
            setOnCheckedChangeListener{ buttonView, isChecked ->

                sharedPref.setNightModeState(isChecked)
                restartApp()

            }
        }

        return view
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

    private fun restartApp() {
        val intent = Intent ((activity as MainActivity).applicationContext, MainActivity::class.java)
        startActivity(intent)
        (activity as MainActivity).finish()
    }

}