package com.example.thingstodo.viewmodel

import android.view.View
import androidx.lifecycle.*
import com.example.thingstodo.storage.dao.ThingToDoDao
import com.example.thingstodo.storage.model.ThingToDo
import kotlinx.coroutines.launch
import java.util.*

class ThingToDoViewModel(private val thingToDoDao: ThingToDoDao) :ViewModel() {

    val allThingsToDo : LiveData<List<ThingToDo>> = thingToDoDao.getThingsToDo().asLiveData()
    val allThingsDone : LiveData<List<ThingToDo>> = thingToDoDao.getThingsDone().asLiveData()

    private fun getNewThingToDoEntry(thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date, thingToDoId:Int = 0, isDone : Boolean = false): ThingToDo{
        return ThingToDo(
            id = thingToDoId,
            name = thingToDoName,
            description = thingToDoDescription,
            timeStamp = thingToDoDate,
            done = isDone
        )
    }

    private fun insertThingToDo(thingToDo: ThingToDo){
        viewModelScope.launch {
            thingToDoDao.insertThingToDo(thingToDo)
        }
    }

    private fun updateThingToDo(thingToDo: ThingToDo){
        viewModelScope.launch{
            thingToDoDao.updateThingToDo(thingToDo)
        }
    }

    private fun deleteThingToDo(thingToDo: ThingToDo) {
        viewModelScope.launch {
            thingToDoDao.deleteThingToDo(thingToDo)
        }
    }

    fun addNewThingToDo(thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date){

        val newThingToDo = getNewThingToDoEntry(thingToDoName, thingToDoDescription, thingToDoDate)
        insertThingToDo(newThingToDo)

    }

    fun getThingToDo(id :Int): LiveData<ThingToDo>{
        return thingToDoDao.getThingToDo(id).asLiveData()
    }

    fun updateNewThingToDo(thingToDoId:Int, thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date, isDone: Boolean){

        val newThingToDo = getNewThingToDoEntry(thingToDoName, thingToDoDescription, thingToDoDate, thingToDoId, isDone)
        updateThingToDo(newThingToDo)
    }

    fun deleteThingToDo(thingToDoId:Int, thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date){

        val newThingToDo = getNewThingToDoEntry(thingToDoName, thingToDoDescription, thingToDoDate, thingToDoId)
        deleteThingToDo(newThingToDo)
    }
}

class ThingToDoViewModelFactor(private val thingToDoDao: ThingToDoDao) : ViewModelProvider.Factory {

    override fun <T: ViewModel?> create (modelClass: Class<T>) : T{
        if(modelClass.isAssignableFrom(ThingToDoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ThingToDoViewModel(thingToDoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}