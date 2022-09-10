package com.example.thingstodo.fragments

import android.os.Bundle
import android.view.*
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thingstodo.R
import com.example.thingstodo.adapter.ToDoAdapter
import com.example.thingstodo.databinding.FragmentToDoBinding
import com.example.thingstodo.model.ThingToDo
import com.example.thingstodo.viewmodel.ThingToDoViewModel

class ToDoFragment(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var viewModel: ThingToDoViewModel? = null
) : Fragment() {
    private var _binding: FragmentToDoBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val showDone : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView (
        inflater : LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ) : View {

        _binding = FragmentToDoBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(ThingToDoViewModel::class.java)

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
                observableList = viewModel!!.allThingsDone as MutableLiveData
                if(activity is AppCompatActivity){
                    (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_to_do_done)
                }
            }else {
                observableList = viewModel!!.allThingsToDo as MutableLiveData
                if(activity is AppCompatActivity){
                    (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_to_do)
                }
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
        viewModel!!.updateNewThingToDo(thingToDo.id, thingToDo.name, thingToDo.description, thingToDo.timeStamp, !thingToDo.done)
    }

    private fun deleteThingToDo(thingToDo: ThingToDo){
        viewModel!!.deleteThingToDo(thingToDo.id, thingToDo.name, thingToDo.description, thingToDo.timeStamp)
    }
}