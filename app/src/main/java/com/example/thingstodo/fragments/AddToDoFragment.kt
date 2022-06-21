package com.example.thingstodo.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.thingstodo.databinding.FragmentAddToDoBinding
import com.example.thingstodo.storage.application.ThingToDoApplication
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import com.example.thingstodo.viewmodel.ThingToDoViewModelFactor
import java.text.SimpleDateFormat
import java.util.*

class AddToDo : Fragment() {
    private var _binding : FragmentAddToDoBinding? = null
    private val binding get() = _binding!!
    private val calender = Calendar.getInstance()

    private val viewModel : ThingToDoViewModel by activityViewModels{
        ThingToDoViewModelFactor(
            (activity?.application as ThingToDoApplication).database
                .thingToDoDao()
        )
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
        val  datePicker = getDatePickerListener()
        val  timePicker = getTimePickerListener()

        binding.dateInput.apply {
            isFocusable = false
            setOnClickListener{ view ->
                DatePickerDialog(
                    requireContext(), datePicker, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        binding.timeInput.apply {
            isFocusable = false
            setOnClickListener{ view ->
                TimePickerDialog(
                    requireContext(), timePicker, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false
                ).show()
            }
        }

        binding.addToDoFab.setOnClickListener{ view ->

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

            if(isError){
                Toast.makeText(requireContext(), "Please Fill in All Fields", Toast.LENGTH_SHORT).show()
            }else{
                System.out.println(binding.titleInput.text)
                System.out.println(binding.descriptionInput.text)
                System.out.println(calender.time)
            }
        }

        return view
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }

    private fun getDateString(calendar: Calendar): String{
        val dateFormat = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        return simpleDateFormat.format(calendar.time)
    }

    private fun getTimeString(calendar: Calendar): String{
        val timeFormat = "HH:mm"
        val simpleDateFormat = SimpleDateFormat(timeFormat)
        return simpleDateFormat.format(calendar.time)
    }

    private fun getTimePickerListener() : TimePickerDialog.OnTimeSetListener {
        return TimePickerDialog.OnTimeSetListener { view, hour, minute ->
            calender.set(Calendar.HOUR, hour)
            calender.set(Calendar.MINUTE, minute)
            calender.set(Calendar.SECOND, 0)
            binding.timeInput.setText(
                getTimeString(calender)
            )
        }
    }

    private fun getDatePickerListener() : DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calender.set(Calendar.HOUR, 13)
            calender.set(Calendar.MINUTE, 24)
            binding.dateInput.setText(
                getDateString(calender)
            )
        }
    }
}