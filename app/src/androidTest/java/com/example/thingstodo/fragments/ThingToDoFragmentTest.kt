package com.example.thingstodo.fragments

import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.thingstodo.R
import com.example.thingstodo.fragments.factory.AndroidTestFragmentFactory
import com.example.thingstodo.utilities.AndroidTestUtilities
import com.google.common.truth.Truth
import org.hamcrest.Matchers
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ThingToDoFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var scenario: FragmentScenario<ThingToDoFragment>

    @Before
    fun setup() {
        val androidTestFragmentFactory = AndroidTestFragmentFactory()
        scenario = launchFragmentInContainer<ThingToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(ThingToDoFragment.ID to 0)
        )
        scenario.moveToState(Lifecycle.State.RESUMED)

    }

    @Test
    fun correctThingToDoNameIsDisplayed() {

        val thingToDoNameTextView = onView(Matchers.allOf(
            withText(AndroidTestUtilities.validThingToDoName),
            isDisplayed()
        ))

        assertThat(thingToDoNameTextView).isNotNull()
    }

    @Test
    fun correctThingToDoDescriptionIsDisplayed() {

        val thingToDoDescriptionTextView = onView(Matchers.allOf(
            withText(AndroidTestUtilities.validThingToDoDescription),
            isDisplayed()
        ))

        assertThat(thingToDoDescriptionTextView).isNotNull()
    }

    @Test
    fun thingToDoDateIsDisplayed() {
        var datetext : String = ""

        scenario.onFragment{
            datetext = it.view!!.findViewById<TextView>(R.id.date_textView).text.toString()
        }

        assertThat(datetext).isNotEmpty()
    }

    @Test // Using Mockito
    fun pressFab_navigateToEditThingToDoFragment_withMockito() {

        val navController = mock(NavController::class.java)
        scenario.onFragment{
            Navigation.setViewNavController(it.requireView(), navController)
        }
        onView(withId(R.id.edit_thing_to_do_fab)).perform(click())

        verify(navController).navigate(
            ThingToDoFragmentDirections.actionThingToDoFragmentToEditToDoFragment(0)
        )
    }

    @Test // Using TestNavHostController
    fun pressFab_navigateToEditThingToDoFragment_withTestNavHostController() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        scenario.onFragment{
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.thingToDoFragment)
            Navigation.setViewNavController(it.requireView(), navController)
        }
        onView(withId(R.id.edit_thing_to_do_fab)).perform(click())

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.editToDoFragment)

    }

    @Test
    fun pressBackButton_navigateToThingToToFragment() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        scenario.onFragment{
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.thingToDoFragment)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.edit_thing_to_do_fab)).perform(click())

        navController.navigateUp()
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.thingToDoFragment)

    }

}