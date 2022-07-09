package com.example.thingstodo.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thingstodo.storage.converters.Converters
import com.example.thingstodo.storage.dao.ThingToDoDao
import com.example.thingstodo.model.ThingToDo

@Database(entities = [ThingToDo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ThingToDoRoomDatabase() :RoomDatabase() {

    abstract fun thingToDoDao() : ThingToDoDao

    companion object {
        @Volatile
        private var INSTANCE : ThingToDoRoomDatabase? = null

        fun getDatabase(context: Context) : ThingToDoRoomDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ThingToDoRoomDatabase::class.java,
                    "thing_to_do_database"
                )
                    .fallbackToDestructiveMigration() //Data will be lost of there is a schema change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}