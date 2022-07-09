package com.example.thingstodo.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ThingToDoReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context!!, "Task due in about 30 min", Toast.LENGTH_LONG).show()
    }
}