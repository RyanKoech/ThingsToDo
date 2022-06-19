package com.example.thingstodo.storage.converters

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimeStamp(value: Long?) : Date?{
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?) :Long?{
        return date?.time
    }
}