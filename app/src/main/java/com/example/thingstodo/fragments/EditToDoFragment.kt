package com.example.thingstodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.thingstodo.application.ThingToDoApplication
import com.example.thingstodo.databinding.FragmentEditToDoBinding
import com.example.thingstodo.utilities.CustomUtility
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import com.example.thingstodo.viewmodel.ThingToDoViewModelFactor
import kotlin.properties.Delegates

class EditToDoFragment : Fragment() {

    companion object {
        val ID = "id"
    }

    private var _binding : FragmentEditToDoBinding? = null
    private val binding get() = _binding!!
    private var thingToDoId by Delegates.notNull<Int>()

    private val viewModel : ThingToDoViewModel by activityViewModels{
        ThingToDoViewModelFactor(
            (activity?.application as ThingToDoApplication).database
                .thingToDoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let {
            thingToDoId = it.getInt(ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

        _binding = FragmentEditToDoBinding.inflate(inflater, container,false)
        val view = binding.root

        viewModel.getThingToDo(thingToDoId).observe(this.viewLifecycleOwner){ thingTodo ->
            binding.titleInput.setText(thingTodo.name)
            binding.descriptionInput.setText(thingTodo.description)
            binding.dateInput.setText(CustomUtility.getFormattedDateString(thingTodo.timeStamp))
            binding.timeInput.setText(CustomUtility.getFormattedTimeString(thingTodo.timeStamp))
        }


        return view
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
}