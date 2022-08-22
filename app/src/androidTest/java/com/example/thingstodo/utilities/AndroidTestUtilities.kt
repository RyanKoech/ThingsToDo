package com.example.thingstodo.utilities

import com.example.thingstodo.model.ThingToDo
import java.util.*

object AndroidTestUtilities {

    val validThingToDoName = "Thing To Do"
    val validThingToDoDescription = "Thing To Do Description"
    val validThingToDoDate : Date = Calendar.getInstance().time
    fun getValidThingToDo(
        id : Int = 0,
        name: String = validThingToDoName,
        description : String  = validThingToDoDescription,
        date: Date = validThingToDoDate,
        done : Boolean = false
    ) = ThingToDo(id, name, description, date, done)

}