package com.example.thingstodo.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thingstodo.MainActivity
import com.example.thingstodo.R
import com.example.thingstodo.adapter.ToDoAdapter
import com.example.thingstodo.application.ThingToDoApplication
import com.example.thingstodo.databinding.FragmentToDoBinding
import com.example.thingstodo.model.ThingToDo
import com.example.thingstodo.viewmodel.ThingToDoViewModel
import com.example.thingstodo.viewmodel.ThingToDoViewModelFactor

class ToDoFragment : Fragment() {
    private var _binding: FragmentToDoBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val showDone : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
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
            val action = R.id.action_toDoFragment_to_addToDo
            this.view?.findNavController()?.navigate(action)
        }

        return view
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?){
        recyclerView = binding.toDoRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ToDoAdapter(requireContext(), ::updateThingToDo, ::deleteThingToDo) { view , id ->
            val action = ToDoFragmentDirections.actionToDoFragmentToThingToDoFragment(id = id)
            view.findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        var observableList = MutableLiveData<List<ThingToDo>>()

        showDone.observe(this.viewLifecycleOwner){ newValue ->
            observableList.removeObservers(this.viewLifecycleOwner)

            if(newValue == true){
                (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_to_do_done)
                observableList = viewModel.allThingsDone as MutableLiveData
            }else {
                observableList = viewModel.allThingsToDo as MutableLiveData
                (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_to_do)
            }
            observableList.observe(this.viewLifecycleOwner) { items ->
                items.let{
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView (){
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.action_show_done).isChecked = showDone.value == true
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {

        return when(item.itemId){
            R.id.action_settings -> {
                val action = ToDoFragmentDirections.actionToDoFragmentToSettingsFragment()
                this.view?.findNavController()?.navigate(action)
                true
            }
            R.id.action_show_done -> {
                if (item.isChecked){
                    item.isChecked = false
                    showDone.value = false
                    System.out.println(showDone.value)
                }
                else{
                    item.isChecked = true
                    showDone.value = true
                    System.out.println(showDone.value)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateThingToDo(thingToDo: ThingToDo){
        viewModel.updateNewThingToDo(thingToDo.id, thingToDo.name, thingToDo.description, thingToDo.timeStamp, !thingToDo.done)
    }

    private fun deleteThingToDo(thingToDo: ThingToDo){
        viewModel.deleteThingToDo(thingToDo.id, thingToDo.name, thingToDo.description, thingToDo.timeStamp)
    }
}