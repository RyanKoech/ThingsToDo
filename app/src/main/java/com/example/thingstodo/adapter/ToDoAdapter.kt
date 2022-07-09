package com.example.thingstodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thingstodo.R
import com.example.thingstodo.model.ThingToDo
import com.google.android.material.card.MaterialCardView
import java.util.*
import kotlin.concurrent.schedule

class ToDoAdapter(
    private val context: Context,
    private val doneCallBack: (thingToDo : ThingToDo) -> Unit,
    private val deleteCallBack: (thingToDo : ThingToDo) -> Unit,
    private val navCallBack: (view : View, id : Int) -> Unit
) : ListAdapter<ThingToDo, ToDoAdapter.ToDoViewHolder>(diffCallBack) {

    class ToDoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val toDoListItem : MaterialCardView = view.findViewById(R.id.to_do_list_item)
        val toDoName : TextView = view.findViewById(R.id.to_do_name)
        val toDoDate : TextView = view.findViewById(R.id.to_do_date)
        val toDoDoneCheckBox : CheckBox = view.findViewById(R.id.to_do_done_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ToDoViewHolder {
        val adapterLayout  = LayoutInflater.from(parent.context)
            .inflate(R.layout.to_do_list_item, parent, false)

        return ToDoViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, postion: Int) {
        val itemToDo = getItem(postion)
        holder.toDoListItem.setOnClickListener{ view ->
            navCallBack(view, itemToDo.id)
        }
        holder.toDoListItem.setOnLongClickListener {  view ->
            deleteCallBack(itemToDo)
            return@setOnLongClickListener true
        }

        var timer = Timer("check_task", false)
        holder.toDoDoneCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

            timer.cancel()
            timer.purge()
            timer = Timer("check_task:"+itemToDo.id, false)

            if(isChecked != itemToDo.done){

                timer.schedule(400){
                    doneCallBack(itemToDo)
                }

            }
        }
        holder.toDoName.text = itemToDo.name
        holder.toDoDate.text = itemToDo.timeStamp.toString()
        holder.toDoDoneCheckBox.isChecked = itemToDo.done
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<ThingToDo> (){
            override fun areItemsTheSame(oldItem: ThingToDo, newItem: ThingToDo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ThingToDo, newItem: ThingToDo): Boolean {
                return (oldItem.name == newItem.name && oldItem.description == newItem.description && oldItem.timeStamp == newItem.timeStamp)
            }

        }
    }

}
