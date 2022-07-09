package com.example.thingstodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "thing_to_do")
data class ThingToDo(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name: String,
    val description: String,
    @ColumnInfo(name = "time_stamp")
    val timeStamp: Date,
    @ColumnInfo(name = "is_done")
    val done : Boolean = false
)