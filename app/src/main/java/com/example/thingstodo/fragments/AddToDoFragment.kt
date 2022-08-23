package com.example.thingstodo.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thingstodo.R
import com.example.thingstodo.databinding.FragmentAddToDoBinding
import com.example.thingstodo.utilities.CustomUtility
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import java.util.*

class AddToDoFragment(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var viewModel: ThingToDoViewModel? = null
) : Fragment() {
    private var _binding : FragmentAddToDoBinding? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val binding get() = _binding!!
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val calender = Calendar.getInstance()

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
        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(ThingToDoViewModel::class.java)
        binding.dateInput.apply {
            val  datePicker = CustomUtility.getDatePickerListener(this, calender)
            isFocusable = false
            setOnClickListener{ view ->
                DatePickerDialog(
                    requireContext(), R.style.CalendarDatePickerDialogStyle, datePicker, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        binding.timeInput.apply {
            val  timePicker = CustomUtility.getTimePickerListener(this, calender)
            isFocusable = false
            setOnClickListener{ view ->
                TimePickerDialog(
                    requireContext(), R.style.CalendarTimePickerDialogStyle, timePicker, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false
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
        viewModel?.addNewThingToDo(binding.titleInput.text.toString(), binding.descriptionInput.text.toString(), calender.time)
    }
}