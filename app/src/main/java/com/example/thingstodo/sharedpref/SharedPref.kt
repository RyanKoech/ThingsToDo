package com.example.thingstodo.sharedpref

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    internal lateinit var sharedPref: SharedPreferences

    init {
        sharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE)
    }

    fun setNightModeState(state: Boolean?){
        val editor : SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean("Night Mode", state!!)
        editor.commit()
    }

    fun loadNightModeState() : Boolean? {
        return sharedPref.getBoolean("Night Mode", false)
    }


}
