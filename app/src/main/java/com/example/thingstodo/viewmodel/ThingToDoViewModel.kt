package com.example.thingstodo.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.thingstodo.storage.dao.ThingToDoDao
import com.example.thingstodo.storage.model.ThingToDo
import kotlinx.coroutines.launch
import java.util.*

class ThingToDoViewModel(private val thingToDoDao: ThingToDoDao) :ViewModel() {

    private fun getNewThingToDoEntry(thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date): ThingToDo{
        return ThingToDo(
            name = thingToDoName,
            description = thingToDoDescription,
            timeStamp = thingToDoDate
        )
    }

    private fun insertThingToDo(thingToDo: ThingToDo){
        viewModelScope.launch {
            thingToDoDao.insertThingToDo(thingToDo)
        }
    }

    fun addNewThingToDo(thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date){

        val newThingToDo = getNewThingToDoEntry(thingToDoName, thingToDoDescription, thingToDoDate)
        insertThingToDo(newThingToDo)

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