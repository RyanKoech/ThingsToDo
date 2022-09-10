package com.example.thingstodo.fragments.factory

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.example.thingstodo.fragments.AddToDoFragment
import com.example.thingstodo.fragments.EditToDoFragment
import com.example.thingstodo.fragments.ThingToDoFragment
import com.example.thingstodo.fragments.ToDoFragment
import com.example.thingstodo.model.ThingToDo
import com.example.thingstodo.repository.FakeThingToDoRepository
import com.example.thingstodo.utilities.AndroidTestUtilities
import com.example.thingstodo.utilities.ContextProvider
import com.example.thingstodo.viewmodel.ThingToDoViewModel

class AndroidTestFragmentFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ToDoFragment::class.java.name -> {
                val contextProvider = object : ContextProvider{
                    override fun getContext(): Context {
                        return InstrumentationRegistry.getInstrumentation().targetContext
                    }
                }
                val repository = FakeThingToDoRepository()
                val viewModel = ThingToDoViewModel(repository, contextProvider)
                ToDoFragment(viewModel)
            }
            ThingToDoFragment::class.java.name -> {
                val initialThingsToDo = listOf<ThingToDo>(
                    AndroidTestUtilities.getValidThingToDo(0),
                )
                val contextProvider = object : ContextProvider{
                    override fun getContext(): Context {
                        return InstrumentationRegistry.getInstrumentation().targetContext
                    }
                }
                val repository = FakeThingToDoRepository(initialThingsToDo)
                val viewModel = ThingToDoViewModel(repository, contextProvider)
                ThingToDoFragment(viewModel)
            }
            AddToDoFragment::class.java.name -> {
                val contextProvider = object : ContextProvider{
                    override fun getContext(): Context {
                        return InstrumentationRegistry.getInstrumentation().targetContext
                    }
                }
                val repository = FakeThingToDoRepository()
                val viewModel = ThingToDoViewModel(repository, contextProvider)
                AddToDoFragment(viewModel)
            }
            EditToDoFragment::class.java.name -> {
                val initialThingsToDo = listOf<ThingToDo>(
                    AndroidTestUtilities.getValidThingToDo(0),
                )
                val contextProvider = object : ContextProvider{
                    override fun getContext(): Context {
                        return InstrumentationRegistry.getInstrumentation().targetContext
                    }
                }
                val repository = FakeThingToDoRepository(initialThingsToDo)
                val viewModel = ThingToDoViewModel(repository, contextProvider)
                EditToDoFragment(viewModel)
            }
            else -> super.instantiate(classLoader, className)
        }
    }

}