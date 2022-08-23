package com.example.thingstodo.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.thingstodo.R
import com.example.thingstodo.fragments.factory.AndroidTestFragmentFactory
import com.example.thingstodo.getOrAwaitValueTest
import com.example.thingstodo.utilities.AndroidTestUtilities
import com.example.thingstodo.utilities.CustomUtility
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AddToDoFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var androidTestFragmentFactory: AndroidTestFragmentFactory

    @Before
    fun setup() {
        androidTestFragmentFactory = AndroidTestFragmentFactory()
    }

    @Test
    fun submitEmptyTitleTextField_setErrorOnTextField() {

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.description_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoDescription)
        )

        onView(withId(R.id.date_input)).perform(
            replaceText(
                CustomUtility.getFormattedDateString(date)
            )
        )

        onView(withId(R.id.time_input)).perform(
            replaceText(
                CustomUtility.getFormattedTimeString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        scenario.onFragment{
            assertThat(it.binding.titleInput.error.toString()).isNotEmpty()
        }

    }

    @Test
    fun submitEmptyDescriptionTextField_setErrorOnTextField() {

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.title_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoName)
        )

        onView(withId(R.id.date_input)).perform(
            replaceText(
                CustomUtility.getFormattedDateString(date)
            )
        )

        onView(withId(R.id.time_input)).perform(
            replaceText(
                CustomUtility.getFormattedTimeString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        scenario.onFragment{
            assertThat(it.binding.descriptionInput.error.toString()).isNotEmpty()
        }

    }

    @Test
    fun submitEmptyDateTextField_setErrorOnTextField() {

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.title_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoName)
        )

        onView(withId(R.id.description_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoDescription)
        )

        onView(withId(R.id.time_input)).perform(
            replaceText(
                CustomUtility.getFormattedTimeString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        scenario.onFragment{
            assertThat(it.binding.dateInput.error.toString()).isNotEmpty()
        }

    }

    @Test
    fun submitEmptyTimeTextField_setErrorOnTextField() {

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.title_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoName)
        )

        onView(withId(R.id.description_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoDescription)
        )

        onView(withId(R.id.date_input)).perform(
            replaceText(
                CustomUtility.getFormattedDateString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        scenario.onFragment{
            assertThat(it.binding.timeInput.error.toString()).isNotEmpty()
        }

    }

    @Test
    fun submitEmptyTitleTextField_noDbInsertion() {

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.description_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoDescription)
        )

        onView(withId(R.id.date_input)).perform(
            replaceText(
                CustomUtility.getFormattedDateString(date)
            )
        )

        onView(withId(R.id.time_input)).perform(
            replaceText(
                CustomUtility.getFormattedTimeString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        scenario.onFragment{
            val thingsToDo = it.viewModel!!.allThingsToDo.getOrAwaitValueTest()
            assertThat(thingsToDo).isEmpty()
        }

    }

    @Test
    fun submitEmptyDescriptionTextField_noDbInsertion() {

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.title_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoName)
        )

        onView(withId(R.id.date_input)).perform(
            replaceText(
                CustomUtility.getFormattedDateString(date)
            )
        )

        onView(withId(R.id.time_input)).perform(
            replaceText(
                CustomUtility.getFormattedTimeString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        scenario.onFragment{
            val thingsToDo = it.viewModel!!.allThingsToDo.getOrAwaitValueTest()
            assertThat(thingsToDo).isEmpty()
        }

    }

    @Test
    fun submitEmptyDateTextField_noDbInsertion() {

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.title_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoName)
        )

        onView(withId(R.id.description_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoDescription)
        )

        onView(withId(R.id.time_input)).perform(
            replaceText(
                CustomUtility.getFormattedTimeString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        scenario.onFragment{
            val thingsToDo = it.viewModel!!.allThingsToDo.getOrAwaitValueTest()
            assertThat(thingsToDo).isEmpty()
        }

    }

    @Test
    fun submitEmptyTimeTextField_noDbInsertion() {

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.title_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoName)
        )

        onView(withId(R.id.description_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoDescription)
        )

        onView(withId(R.id.date_input)).perform(
            replaceText(
                CustomUtility.getFormattedDateString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        scenario.onFragment{
            val thingsToDo = it.viewModel!!.allThingsToDo.getOrAwaitValueTest()
            assertThat(thingsToDo).isEmpty()
        }

    }

    @Test
    fun submitValidInputs_InsertItemToDb() {

        val navController = mock(NavController::class.java)

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.title_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoName)
        )

        onView(withId(R.id.description_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoDescription)
        )

        onView(withId(R.id.date_input)).perform(
            replaceText(
                CustomUtility.getFormattedDateString(date)
            )
        )

        onView(withId(R.id.time_input)).perform(
            replaceText(
                CustomUtility.getFormattedTimeString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        scenario.onFragment{
            val thingsToDo = it.viewModel!!.allThingsToDo.getOrAwaitValueTest()
            assertThat(thingsToDo).isNotEmpty()
        }

    }

    @Test
    fun submitValidInputs_NavigateToToDoFragment() {

        val navController = mock(NavController::class.java)

        val scenario = launchFragmentInContainer<AddToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo
        )

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        val date = AndroidTestUtilities.validThingToDoDate

        onView(withId(R.id.title_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoName)
        )

        onView(withId(R.id.description_input)).perform(
            replaceText(AndroidTestUtilities.validThingToDoDescription)
        )

        onView(withId(R.id.date_input)).perform(
            replaceText(
                CustomUtility.getFormattedDateString(date)
            )
        )

        onView(withId(R.id.time_input)).perform(
            replaceText(
                CustomUtility.getFormattedTimeString(date)
            )
        )

        onView(withId(R.id.add_to_do_fab)).perform(
            click()
        )

        verify(navController).navigate(
            AddToDoFragmentDirections.actionAddToDoToToDoFragment()
        )
    }

}