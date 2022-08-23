package com.example.thingstodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thingstodo.databinding.FragmentThingToDoBinding
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import kotlin.properties.Delegates

class ThingToDoFragment(
    private var viewModel : ThingToDoViewModel? = null
) : Fragment() {

    companion object {
        var ID = "id"
        val TAG = "ThingToDoFragment"
    }

    private var _binding : FragmentThingToDoBinding? = null
    private val binding get() = _binding!!
    private var thingToDoId by Delegates.notNull<Int>()

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
        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(ThingToDoViewModel::class.java)
        viewModel!!.getThingToDo(thingToDoId).observe(this.viewLifecycleOwner) { thingToDo ->
            thingToDo.let{
                binding.titleTextView.text = thingToDo.name
                binding.descriptionTextView.text = thingToDo.description
                binding.dateTextView.text = thingToDo.timeStamp.toString()
            }
        }

        binding.editThingToDoFab.setOnClickListener{ view ->
            val action = ThingToDoFragmentDirections.actionThingToDoFragmentToEditToDoFragment(id = thingToDoId)
            this.view?.findNavController()?.navigate(action)
        }

        return view
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

}