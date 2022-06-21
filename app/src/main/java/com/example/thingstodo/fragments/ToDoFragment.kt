package com.example.thingstodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thingstodo.R
import com.example.thingstodo.adapter.ToDoAdapter
import com.example.thingstodo.application.ThingToDoApplication
import com.example.thingstodo.databinding.FragmentToDoBinding
import com.example.thingstodo.storage.model.ThingToDo
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import com.example.thingstodo.viewmodel.ThingToDoViewModelFactor
import com.google.android.material.snackbar.Snackbar
import java.sql.Date
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ToDoFragment : Fragment() {
    private var _binding: FragmentToDoBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    private val viewModel : ThingToDoViewModel by activityViewModels{
        ThingToDoViewModelFactor(
            (activity?.application as ThingToDoApplication).database
                .thingToDoDao()
        )
    }

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
        val adapter = ToDoAdapter(requireContext())
        recyclerView.adapter = adapter
        viewModel.allThingsToDo.observe(this.viewLifecycleOwner) { items ->
            items.let{
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView (){
        super.onDestroyView()
        _binding = null
    }

}