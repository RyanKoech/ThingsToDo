package com.example.thingstodo.storage.dao

import androidx.room.*
import com.example.thingstodo.model.ThingToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ThingToDoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertThingToDo(thingToDo: ThingToDo) : Long

    @Update
    suspend fun updateThingToDo(thingToDo: ThingToDo)

    @Delete
    suspend fun deleteThingToDo(thingToDo: ThingToDo)

    @Query("SELECT * FROM thing_to_do WHERE id = :id")
    fun observeThingToDo(id: Int) : Flow<ThingToDo>

    @Query("SELECT * FROM thing_to_do WhERE is_done = 0 ORDER BY time_stamp ASC")
    fun observeThingsToDo() : Flow<List<ThingToDo>>

    @Query("SELECT * FROM thing_to_do WhERE is_done = 1 ORDER BY time_stamp ASC")
    fun observeThingsDone() : Flow<List<ThingToDo>>
}