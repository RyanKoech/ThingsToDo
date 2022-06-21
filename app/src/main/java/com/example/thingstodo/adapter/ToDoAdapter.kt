package com.example.thingstodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thingstodo.R
import com.example.thingstodo.storage.model.ThingToDo

class ToDoAdapter(
    private val context: Context,
    private val thingsDoTo : List<ThingToDo>
) : ListAdapter<ThingToDo, ToDoAdapter.ToDoViewHolder>(diffCallBack) {

    class ToDoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        // val toDoListItem : MaterialCardView = view.findViewById(R.id.to_do_list_item)
        val toDoName : TextView = view.findViewById(R.id.to_do_name)
        val toDoDate : TextView = view.findViewById(R.id.to_do_date)
    }

    override fun getItemCount(): Int {
        return thingsDoTo.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ToDoViewHolder {
        val adapterLayout  = LayoutInflater.from(parent.context)
            .inflate(R.layout.to_do_list_item, parent, false)

        return ToDoViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, postion: Int) {
        val itemToDo = thingsDoTo[postion]
        holder.toDoName.text = itemToDo.name
        holder.toDoDate.text = itemToDo.timeStamp.toString()
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
