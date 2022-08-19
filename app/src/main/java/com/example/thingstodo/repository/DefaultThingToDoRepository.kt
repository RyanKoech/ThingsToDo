package com.example.thingstodo.repository

import com.example.thingstodo.model.ThingToDo
import com.example.thingstodo.storage.dao.ThingToDoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultThingToDoRepository @Inject constructor(
    private val thingToDoDao: ThingToDoDao
): ThingToDoRepository {
    override suspend fun insertThingToDo(thingToDo: ThingToDo): Long = thingToDoDao.insertThingToDo(thingToDo)

    override suspend fun updateThingToDo(thingToDo: ThingToDo) = thingToDoDao.updateThingToDo(thingToDo)

    override suspend fun deleteThingToDo(thingToDo: ThingToDo) = thingToDoDao.deleteThingToDo(thingToDo)

    override fun observeThingToDo(id: Int): Flow<ThingToDo> = thingToDoDao.observeThingToDo(id)

    override fun observeThingsToDo(): Flow<List<ThingToDo>> = thingToDoDao.observeThingsToDo()

    override fun observeThingsDone(): Flow<List<ThingToDo>> = thingToDoDao.observeThingsDone()
}