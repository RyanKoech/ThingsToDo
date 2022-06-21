package com.example.thingstodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.thingstodo.databinding.FragmentAddToDoBinding
import com.example.thingstodo.storage.application.ThingToDoApplication
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import com.example.thingstodo.viewmodel.ThingToDoViewModelFactor

class AddToDo : Fragment() {
    private var _binding : FragmentAddToDoBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ThingToDoViewModel by activityViewModels{
        ThingToDoViewModelFactor(
            (activity?.application as ThingToDoApplication).database
                .thingToDoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {

        _binding = FragmentAddToDoBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }
}