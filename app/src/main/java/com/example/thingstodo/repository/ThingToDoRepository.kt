package com.example.thingstodo.repository

import com.example.thingstodo.model.ThingToDo
import kotlinx.coroutines.flow.Flow

interface ThingToDoRepository {

    suspend fun insertThingToDo(thingToDo: ThingToDo) : Long

    suspend fun updateThingToDo(thingToDo: ThingToDo)

    suspend fun deleteThingToDo(thingToDo: ThingToDo)

    fun observeThingToDo(id: Int) : Flow<ThingToDo>

    fun observeThingsToDo() : Flow<List<ThingToDo>>

    fun observeThingsDone() : Flow<List<ThingToDo>>

}