package com.example.thingstodo.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.thingstodo.model.ThingToDo
import kotlinx.coroutines.flow.Flow

class FakeThingToDoRepository(
    initialThingsToDo : List<ThingToDo> = listOf()
) : ThingToDoRepository {

    private var thingsToDo : MutableList<ThingToDo> = mutableListOf<ThingToDo>()

    private var nextThingToDoId = initialThingsToDo.size

    private val observableThingToDo = MutableLiveData<ThingToDo>()

    private val observableThingsToDo = MutableLiveData<List<ThingToDo>>(thingsToDo.filter { it.done })

    private val observableThingsDone = MutableLiveData<List<ThingToDo>>(thingsToDo.filter { !it.done })

    init {
        initialThingsToDo.forEach{ initialThingToDo ->
            thingsToDo.add(initialThingToDo)
        }
    }

    override suspend fun insertThingToDo(thingToDo: ThingToDo): Long {
        val nextThingToDo = getNextThingToDo(thingToDo)
        thingsToDo.add(nextThingToDo)
        refreshData()
        return nextThingToDo.id.toLong()
    }

    override suspend fun updateThingToDo(thingToDo: ThingToDo) {
        val newThingsToDo = thingsToDo.map {
            if(it.id == thingToDo.id) thingToDo else it
        }
        thingsToDo = newThingsToDo.toMutableList()
        refreshData()
    }

    override suspend fun deleteThingToDo(thingToDo: ThingToDo) {
        thingsToDo.remove(thingToDo)
        refreshData()
    }

    override fun observeThingToDo(id: Int): Flow<ThingToDo> {
        val thingToDo : ThingToDo = thingsToDo.filter { it.id == id }[0]
        observableThingToDo.postValue(thingToDo)
        return observableThingToDo.asFlow()
    }

    override fun observeThingsToDo(): Flow<List<ThingToDo>> {
        return observableThingsToDo.asFlow()
    }

    override fun observeThingsDone(): Flow<List<ThingToDo>> {
        return observableThingsDone.asFlow()
    }

    private fun refreshData () {
        observableThingsToDo.postValue(thingsToDo.filter { !it.done })
        observableThingsDone.postValue(thingsToDo.filter { it.done })

    }

    private fun getNextThingToDo(thingToDo: ThingToDo) :ThingToDo {
        val nextThingToDo = ThingToDo(nextThingToDoId, thingToDo.name, thingToDo.description, thingToDo.timeStamp, thingToDo.done)
        nextThingToDoId++

        return nextThingToDo
    }
}