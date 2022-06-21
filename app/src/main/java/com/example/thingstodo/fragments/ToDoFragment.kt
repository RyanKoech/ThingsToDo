package com.example.thingstodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thingstodo.R
import com.example.thingstodo.adapter.ToDoAdapter
import com.example.thingstodo.databinding.FragmentToDoBinding
import com.example.thingstodo.storage.model.ThingToDo
import com.google.android.material.snackbar.Snackbar
import java.sql.Date
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

val thingsToDo : List<ThingToDo> = listOf(
    ThingToDo(1, "Follow up with Judy" , "Follow up on activation to happen in comming month if April 2020", Calendar.getInstance().time),
    ThingToDo(2, "Follow up with James" , "Follow up on activation to happen in comming month if April 2020", Calendar.getInstance().time),
    ThingToDo(3, "Follow up with John" , "Follow up on activation to happen in comming month if April 2020", Calendar.getInstance().time),
    ThingToDo(4, "Follow up with Jacky" , "Follow up on activation to happen in comming month if April 2020", Calendar.getInstance().time)
)

class ToDoFragment : Fragment() {
    private var _binding: FragmentToDoBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView (
        inflater : LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ) : View? {

        _binding = FragmentToDoBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.toDoListFab.setOnClickListener{ view ->
            val action = R.id.action_toDoFragment2_to_addToDo
            this.view?.findNavController()?.navigate(action)
        }

        return view
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?){
        recyclerView = binding.toDoRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ToDoAdapter(requireContext(), thingsToDo)
    }

    override fun onDestroyView (){
        super.onDestroyView()
        _binding = null
    }

}