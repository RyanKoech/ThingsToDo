package com.example.thingstodo.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.thingstodo.R
import com.example.thingstodo.adapter.ToDoAdapter
import com.example.thingstodo.fragments.factory.AndroidTestFragmentFactory
import com.example.thingstodo.utilities.AndroidTestUtilities
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
class ToDoFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var scenario: FragmentScenario<ToDoFragment>
    private lateinit var navController : NavController

    @Before
    fun setup() {

        navController = mock(NavController::class.java)
        val androidTestFragmentFactory = AndroidTestFragmentFactory()
        scenario = launchFragmentInContainer<ToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        scenario.onFragment{
            Navigation.setViewNavController(it.view!!, navController)
        }

    }

    @Test
    fun fabViewIsDisplayed() {

        val fab = onView(Matchers.allOf(
            withId(R.id.to_do_list_fab),
            isDisplayed()
        ))

        assertThat(fab).isNotNull()
    }

    @Test
    fun recyclerViewIsDisplayed() {

        val recyclerView = onView(Matchers.allOf(
            withId(R.id.to_do_recycler_view),
            isDisplayed()
        ))

        assertThat(recyclerView).isNotNull()
    }

    @Test
    fun pressFab_navigateToAddThingToDoFragment() {

        onView(withId(R.id.to_do_list_fab)).perform(click())

        verify(navController).navigate(
            R.id.action_toDoFragment_to_addToDo
        )
    }

    @Test
    fun pressActionbarSettingsIcon_navigateToSettingsFragment() {
        // TODO : Investigate why the actionbar is not being displayed during testing

        onView(withId(R.id.action_settings)).perform(click())

        verify(navController).navigate(
            ToDoFragmentDirections.actionToDoFragmentToSettingsFragment()
        )
    }

    @Test
    fun clickThingToDoListItem_navigateToThingToDoFragment() {

        scenario.onFragment{
            // it.viewModels<ThingToDoViewModel>().value.addNewThingToDo(AndroidTestUtilities.validThingToDoName, AndroidTestUtilities.validThingToDoDescription, AndroidTestUtilities.validThingToDoDate)
            it.viewModel!!.addNewThingToDo(AndroidTestUtilities.validThingToDoName, AndroidTestUtilities.validThingToDoDescription, AndroidTestUtilities.validThingToDoDate)
        }

        onView(withId(R.id.to_do_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ToDoAdapter.ToDoViewHolder>(
                0,
                click()
            )
        )

        verify(navController).navigate(
            ToDoFragmentDirections.actionToDoFragmentToThingToDoFragment(id = 0)
        )
    }
}