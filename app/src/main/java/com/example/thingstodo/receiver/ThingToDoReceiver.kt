package com.example.thingstodo.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.thingstodo.other.Constants

class ThingToDoReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val thingToDoName : String = intent?.getStringExtra(Constants.TAG_TASK_NAME).toString()
        Toast.makeText(context!!, "Task due in about 30 min", Toast.LENGTH_LONG).show()
    }
}