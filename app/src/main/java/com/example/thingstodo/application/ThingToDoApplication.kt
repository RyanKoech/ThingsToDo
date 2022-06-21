package com.example.thingstodo.application

import android.app.Application
import com.example.thingstodo.storage.database.ThingToDoRoomDatabase

class ThingToDoApplication : Application() {

    val database : ThingToDoRoomDatabase by lazy {
        ThingToDoRoomDatabase.getDatabase(this)
    }
}