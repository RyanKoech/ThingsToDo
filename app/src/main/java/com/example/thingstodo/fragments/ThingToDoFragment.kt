package com.example.thingstodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.thingstodo.R
import com.example.thingstodo.application.ThingToDoApplication
import com.example.thingstodo.databinding.FragmentThingToDoBinding
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import com.example.thingstodo.viewmodel.ThingToDoViewModelFactor
import kotlin.properties.Delegates

class ThingToDoFragment : Fragment() {

    companion object {
        var ID = "id"
    }

    private var _binding : FragmentThingToDoBinding? = null
    private val binding get() = _binding!!
    private var thingToDoId by Delegates.notNull<Int>()

    private val viewModel : ThingToDoViewModel by activityViewModels{
        ThingToDoViewModelFactor(
            (activity?.application as ThingToDoApplication).database
                .thingToDoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let{
            thingToDoId = it.getInt(ID)
        }
    }

    override fun onCreateView(inflater : LayoutInflater, container: ViewGroup?, savedInstaceState: Bundle? ): View?{

        _binding = FragmentThingToDoBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.getThingToDo(thingToDoId).observe(this.viewLifecycleOwner) { thingToDo ->
            thingToDo.let{
                binding.titleTextView.text = thingToDo.name
                binding.descriptionTextView.text = thingToDo.description
                binding.dateTextView.text = thingToDo.timeStamp.toString()
            }
        }

        return view
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

}