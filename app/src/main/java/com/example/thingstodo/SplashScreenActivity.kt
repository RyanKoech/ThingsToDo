package com.example.thingstodo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.thingstodo.sharedpref.SharedPref

class SplashScreenActivity : AppCompatActivity() {

    lateinit var handler: Handler
    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPref = SharedPref(this)
        if(sharedPref.loadNightModeState()!!){
            System.out.println("Apply Dark Mode")
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }else{
            System.out.println("Apply Light Mode")
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.setDecorFitsSystemWindows(false)
        }else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }

        supportActionBar?.hide()

        //PROGRAMMATIC APPROACH FOR UPDATING STATUS BAR ICON COLORS
        // window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        // val decorView = this.window.decorView
        // decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)

    }
}