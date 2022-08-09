package com.example.thingstodo.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.example.thingstodo.MainActivity
import com.example.thingstodo.R
import com.example.thingstodo.fragments.ThingToDoFragment
import com.example.thingstodo.other.Constants

class ThingToDoReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val thingToDoName : String = intent.getStringExtra(Constants.TAG_TASK_NAME).toString()
        val thingToDoId: Int = intent.getIntExtra(Constants.TAG_TASK_ID, 0)

        val activityIntent = Intent(context, MainActivity::class.java)
        activityIntent.putExtra(Constants.INITIAL_FRAGMENT, ThingToDoFragment.TAG)
        activityIntent.putExtra(ThingToDoFragment.ID, thingToDoId)
        val notificationManager = NotificationManagerCompat.from(context)

        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(activityIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        // val pendingIntent = PendingIntent.getActivity(
        //     context,
        //     0,
        //     activityIntent,
        //     if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        // )

        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATIONS_CHANNEL_ID)
                .setContentTitle("Task due in 30 minutes")
                .setContentText(thingToDoName)
                .setSmallIcon(R.drawable.logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()


        notificationManager.notify(thingToDoId, notification)

    }
}