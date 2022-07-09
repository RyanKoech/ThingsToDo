package com.example.thingstodo.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.thingstodo.MainActivity
import com.example.thingstodo.R
import com.example.thingstodo.application.ThingToDoApplication
import com.example.thingstodo.databinding.FragmentEditToDoBinding
import com.example.thingstodo.utilities.CustomUtility
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import com.example.thingstodo.viewmodel.ThingToDoViewModelFactor
import java.util.*
import kotlin.properties.Delegates

class EditToDoFragment : Fragment() {

    companion object {
        val ID = "id"
    }

    private var _binding : FragmentEditToDoBinding? = null
    private val binding get() = _binding!!
    private var thingToDoId by Delegates.notNull<Int>()
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

        arguments?.let {
            thingToDoId = it.getInt(ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

        _binding = FragmentEditToDoBinding.inflate(inflater, container,false)
        val view = binding.root

        viewModel.getThingToDo(thingToDoId).observe(this.viewLifecycleOwner){ thingTodo ->
            calender.time = thingTodo.timeStamp
            binding.titleInput.setText(thingTodo.name)
            binding.descriptionInput.setText(thingTodo.description)
            binding.dateInput.setText(CustomUtility.getFormattedDateString(thingTodo.timeStamp))
            binding.timeInput.setText(CustomUtility.getFormattedTimeString(thingTodo.timeStamp))
            binding.doneCheckbox.isChecked = thingTodo.done
        }

        binding.dateInput.apply {
            val  datePicker = CustomUtility.getDatePickerListener(this, calender)
            isFocusable = false
            setOnClickListener{ view ->
                DatePickerDialog(
                    requireContext(), datePicker, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(
                        Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        binding.timeInput.apply {
            val  timePicker = CustomUtility.getTimePickerListener(this, calender)
            isFocusable = false
            setOnClickListener{ view ->
                TimePickerDialog(
                    requireContext(), timePicker, calender.get(Calendar.HOUR_OF_DAY), calender.get(
                        Calendar.MINUTE), false
                ).show()
            }
        }

        binding.editToDoFab.setOnClickListener{ view ->

            var isError = areFieldsEmpty()

            if(isError){
                Toast.makeText(requireContext(), "Please Fill in All Fields", Toast.LENGTH_SHORT).show()
            }else{
                updateThingToDo()
                val action = EditToDoFragmentDirections.actionEditToDoFragmentToToDoFragment()
                this.view?.findNavController()?.navigate(action)
            }
        }

        return view
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu : Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.edit_to_do_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId){
            R.id.action_delete_to_do -> {
                deleteThingToDo()
                val action = EditToDoFragmentDirections.actionEditToDoFragmentToToDoFragment()
                this.view?.findNavController()?.navigate(action)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    private fun updateThingToDo(){
        viewModel.updateNewThingToDo(thingToDoId, binding.titleInput.text.toString(), binding.descriptionInput.text.toString(), calender.time, binding.doneCheckbox.isChecked)
    }

    private fun deleteThingToDo(){
        viewModel.deleteThingToDo(thingToDoId, binding.titleInput.text.toString(), binding.descriptionInput.text.toString(), calender.time)
    }

}