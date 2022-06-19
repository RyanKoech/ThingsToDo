package com.example.thingstodo.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "thing_to_do")
data class ThingToDo(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val description: String,
    @ColumnInfo(name = "date_time")
    val datetime: Date
)