package com.example.thingstodo.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.lifecycle.*
import com.example.thingstodo.model.ThingToDo
import com.example.thingstodo.other.Constants
import com.example.thingstodo.other.Event
import com.example.thingstodo.other.Resource
import com.example.thingstodo.receiver.ThingToDoReceiver
import com.example.thingstodo.repository.ThingToDoRepository
import com.example.thingstodo.utilities.ContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ThingToDoViewModel @Inject constructor(
    private val thingToDoDaoRepository: ThingToDoRepository,
    private val contextProvider: ContextProvider
    ) : ViewModel() {

    val allThingsToDo : LiveData<List<ThingToDo>> = thingToDoDaoRepository.observeThingsToDo().asLiveData()
    val allThingsDone : LiveData<List<ThingToDo>> = thingToDoDaoRepository.observeThingsDone().asLiveData()
    private val _getNewThingToDoStatus = MutableLiveData<Event<Resource<ThingToDo>>>()
    val getNewThingToDoStatus : LiveData<Event<Resource<ThingToDo>>> get() = _getNewThingToDoStatus
    private val alarmManager: AlarmManager =  contextProvider.getContext().getSystemService(ALARM_SERVICE) as AlarmManager

    @TestOnly
    internal fun getNewThingToDoEntry(thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date, thingToDoId:Int = 0, isDone : Boolean = false): ThingToDo? {

        if(thingToDoName.isBlank() || thingToDoDescription.isBlank()){
            _getNewThingToDoStatus.postValue(Event(Resource.error("The string values should not be blank", null)))
            return null
        }

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
            val thingToDoId = thingToDoDaoRepository.insertThingToDo(thingToDo)
            scheduleReminder(thingToDoId.toInt(), thingToDo.name, thingToDo.timeStamp.time)
        }
    }

    private fun updateThingToDo(thingToDo: ThingToDo){
        viewModelScope.launch{
            thingToDoDaoRepository.updateThingToDo(thingToDo)
        }
    }

    private fun deleteThingToDo(thingToDo: ThingToDo) {
        viewModelScope.launch {
            thingToDoDaoRepository.deleteThingToDo(thingToDo)
        }
    }

    private fun scheduleReminder(thingToDoId: Int, thingToDoName: String, time: Long) {
        val intent = Intent(contextProvider.getContext(), ThingToDoReceiver::class.java)
        intent.putExtra(Constants.TAG_TASK_NAME, thingToDoName)
        intent.putExtra(Constants.TAG_TASK_ID, thingToDoId)
        val pendingIntent = PendingIntent.getBroadcast(contextProvider.getContext(), thingToDoId, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val calendar = Calendar.getInstance()
        println(calendar)
        alarmManager.set(
            AlarmManager.RTC,
            // calendar.timeInMillis - Constants.MILLISECONDS_30MIN,
            time - Constants.MILLISECONDS_30MIN,
            pendingIntent
        )

        // Toast.makeText(contextProvider.getContext(), "Toast Scheduled successfully", Toast.LENGTH_SHORT).show()
    }

    private fun cancelReminder(thingToDoId: Int) {
        val intent = Intent(contextProvider.getContext(), ThingToDoReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(contextProvider.getContext(), thingToDoId, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.cancel(
            pendingIntent
        )

        // Toast.makeText(contextProvider.getContext(), "Scheduled deleted successfully", Toast.LENGTH_SHORT).show()
    }

    fun addNewThingToDo(thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date){

        val newThingToDo = getNewThingToDoEntry(thingToDoName, thingToDoDescription, thingToDoDate)
        newThingToDo?.let {
            insertThingToDo(it)
        }
    }

    fun getThingToDo(id :Int): LiveData<ThingToDo>{
        return thingToDoDaoRepository.observeThingToDo(id).asLiveData()
    }

    fun updateNewThingToDo(thingToDoId:Int, thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date, isDone: Boolean){

        val newThingToDo = getNewThingToDoEntry(thingToDoName, thingToDoDescription, thingToDoDate, thingToDoId, isDone)
        newThingToDo?.let {
            updateThingToDo(it)
            cancelReminder(thingToDoId)
            scheduleReminder(thingToDoId, thingToDoName, thingToDoDate.time)
        }
    }

    fun deleteThingToDo(thingToDoId:Int, thingToDoName : String, thingToDoDescription: String, thingToDoDate: Date){

        val newThingToDo = getNewThingToDoEntry(thingToDoName, thingToDoDescription, thingToDoDate, thingToDoId)
        newThingToDo?.let{
            deleteThingToDo(it)
            cancelReminder(thingToDoId)
        }
    }
}