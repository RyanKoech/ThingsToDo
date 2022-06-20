package com.example.thingstodo.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thingstodo.storage.dao.ThingToDoDao

class ThingToDoViewModel(private val thingToDoDao: ThingToDoDao) :ViewModel() {}

class ThingToDoViewModelFactor(private val thingToDoDao: ThingToDoDao) : ViewModelProvider.Factory {

    override fun <T: ViewModel?> create (modelClass: Class<T>) : T{
        if(modelClass.isAssignableFrom(ThingToDoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ThingToDoViewModel(thingToDoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}