package com.example.thingstodo.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.thingstodo.MainActivity
import com.example.thingstodo.databinding.FragmentAddToDoBinding
import com.example.thingstodo.application.ThingToDoApplication
import com.example.thingstodo.utilities.CustomUtility
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import com.example.thingstodo.viewmodel.ThingToDoViewModelFactor
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class AddToDoFragment : Fragment() {
    private var _binding : FragmentAddToDoBinding? = null
    private val binding get() = _binding!!
    private val calender = Calendar.getInstance()
    private lateinit var activity: MainActivity
    private lateinit var viewModel : ThingToDoViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity

        val viewModel : ThingToDoViewModel by activityViewModels{
            ThingToDoViewModelFactor(
                (activity?.application as ThingToDoApplication).database
                    .thingToDoDao() ,
                activity
            )
        }
        this.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {

        _binding = FragmentAddToDoBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.dateInput.apply {
            val  datePicker = CustomUtility.getDatePickerListener(this, calender)
            isFocusable = false
            setOnClickListener{ view ->
                DatePickerDialog(
                    requireContext(), datePicker, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        binding.timeInput.apply {
            val  timePicker = CustomUtility.getTimePickerListener(this, calender)
            isFocusable = false
            setOnClickListener{ view ->
                TimePickerDialog(
                    requireContext(), timePicker, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false
                ).show()
            }
        }

        binding.addToDoFab.setOnClickListener{ view ->

            var isError = areFieldsEmpty()

            if(isError){
                Toast.makeText(requireContext(), "Please Fill in All Fields", Toast.LENGTH_SHORT).show()
            }else{
                addNewItem()
                val action = AddToDoFragmentDirections.actionAddToDoToToDoFragment()
                this.view?.findNavController()?.navigate(action)
            }
        }

        return view
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }

    private fun areFieldsEmpty() : Boolean{

        var isError = false

        binding.titleInput.apply {
            if (text.toString().trim().isEmpty() || text.toString().trim().isBlank() ){
                error = "Should not be empty"
                isError = true
            }
        }

        binding.descriptionInput.apply {
            if (text.toString().trim().isEmpty() || text.toString().trim().isBlank() ){
                error = "should not be empty"
                isError = true
            }
        }

        binding.dateInput.apply {
            if (text.toString().trim().isEmpty() || text.toString().trim().isBlank() ){
                error = "should not be empty"
                isError = true
            }
        }

        binding.timeInput.apply {
            if (text.toString().trim().isEmpty() || text.toString().trim().isBlank() ){
                error = "should not be empty"
                isError = true
            }
        }

        return isError
    }

    private fun addNewItem() {
        viewModel.addNewThingToDo(binding.titleInput.text.toString(), binding.descriptionInput.text.toString(), calender.time)
    }
}