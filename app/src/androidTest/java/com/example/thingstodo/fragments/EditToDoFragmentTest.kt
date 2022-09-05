package com.example.thingstodo.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.thingstodo.R
import com.example.thingstodo.fragments.factory.AndroidTestFragmentFactory
import com.example.thingstodo.getOrAwaitValueTest
import com.example.thingstodo.model.ThingToDo
import com.example.thingstodo.utilities.AndroidTestUtilities
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class EditToDoFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var androidTestFragmentFactory: AndroidTestFragmentFactory

    @Before
    fun setup() {
        androidTestFragmentFactory = AndroidTestFragmentFactory()
    }

    @Test
    fun submitEmptyTitleTextField_setErrorOnTextField() {
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment{
            Navigation.setViewNavController(it.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.title_input)).perform(
            ViewActions.replaceText("")
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            Truth.assertThat(it.binding.titleInput.error).isNotNull()
        }
    }

    @Test
    fun submitEmptyDescriptionTextField_setErrorOnTextField() {
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment{
            Navigation.setViewNavController(it.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.description_input)).perform(
            ViewActions.replaceText("")
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            Truth.assertThat(it.binding.descriptionInput.error).isNotNull()
        }
    }

    @Test
    fun submitEmptyDateTextField_setErrorOnTextField() {
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment{
            Navigation.setViewNavController(it.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.date_input)).perform(
            ViewActions.replaceText("")
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            Truth.assertThat(it.binding.dateInput.error).isNotNull()
        }
    }

    @Test
    fun submitEmptyTimeTextField_setErrorOnTextField() {
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment{
            Navigation.setViewNavController(it.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.time_input)).perform(
            ViewActions.replaceText("")
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            Truth.assertThat(it.binding.timeInput.error).isNotNull()
        }
    }

    @Test
    fun submitEmptyTitleTextField_noDbUpdate() {

        var oldThingToDo : ThingToDo? = null
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            oldThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
        }

        Espresso.onView(ViewMatchers.withId(R.id.title_input)).perform(
            ViewActions.replaceText("")
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            val newThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
            Truth.assertThat(newThingToDo).isEqualTo(oldThingToDo)
        }

    }

    @Test
    fun submitEmptyDescriptionTextField_noDbUpdate() {

        var oldThingToDo : ThingToDo? = null
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            oldThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
        }

        Espresso.onView(ViewMatchers.withId(R.id.description_input)).perform(
            ViewActions.replaceText("")
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            val newThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
            Truth.assertThat(newThingToDo).isEqualTo(oldThingToDo)
        }

    }

    @Test
    fun submitEmptyDateTextField_noDbUpdate() {

        var oldThingToDo : ThingToDo? = null
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            oldThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
        }

        Espresso.onView(ViewMatchers.withId(R.id.date_input)).perform(
            ViewActions.replaceText("")
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            val newThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
            Truth.assertThat(newThingToDo).isEqualTo(oldThingToDo)
        }

    }

    @Test
    fun submitEmptyTimeTextField_noDbUpdate() {

        var oldThingToDo : ThingToDo? = null
        val navController = Mockito.mock(NavController::class.java)

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            oldThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
        }

        Espresso.onView(ViewMatchers.withId(R.id.time_input)).perform(
            ViewActions.replaceText("")
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            val newThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
            Truth.assertThat(newThingToDo).isEqualTo(oldThingToDo)
        }

    }

    @Test
    fun submitValidTitleTextField_dbUpdateTitle() {

        val navController = Mockito.mock(NavController::class.java)
        val newValidThingToDoName = "${AndroidTestUtilities.validThingToDoName}1"
        var oldThingToDo : ThingToDo = AndroidTestUtilities.getValidThingToDo(
            name = newValidThingToDoName
        )

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.title_input)).perform(
            ViewActions.replaceText(newValidThingToDoName)
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            val newThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
            Truth.assertThat(newThingToDo).isEqualTo(oldThingToDo)
        }
    }

    @Test
    fun submitValidDescriptionTextField_dbUpdateTitle() {

        val navController = Mockito.mock(NavController::class.java)
        val newValidThingToDoDescription = "${AndroidTestUtilities.validThingToDoDescription}1"
        var oldThingToDo : ThingToDo = AndroidTestUtilities.getValidThingToDo(
            description = newValidThingToDoDescription
        )

        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.description_input)).perform(
            ViewActions.replaceText(newValidThingToDoDescription)
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            val newThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
            Truth.assertThat(newThingToDo).isEqualTo(oldThingToDo)
        }
    }

    @Test
    fun submitValidDateTextField_dbUpdateTitle() {

        val navController = Mockito.mock(NavController::class.java)
        var newValidThingToDoDate : Date? = null
        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            it.calender.add(Calendar.DAY_OF_MONTH, 10)
            newValidThingToDoDate = it.calender.time
        }

        val oldThingToDo : ThingToDo = AndroidTestUtilities.getValidThingToDo(
            date = newValidThingToDoDate!!
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            val newThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
            Truth.assertThat(newThingToDo).isEqualTo(oldThingToDo)
        }
    }

    @Test
    fun submitValidTimeTextField_dbUpdateTitle() {

        val navController = Mockito.mock(NavController::class.java)
        var newValidThingToDoDate : Date? = null
        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            it.calender.add(Calendar.MINUTE, 10)
            newValidThingToDoDate = it.calender.time
        }

        val oldThingToDo : ThingToDo = AndroidTestUtilities.getValidThingToDo(
            date = newValidThingToDoDate!!
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            val newThingToDo = it.viewModel!!.getThingToDo(0).getOrAwaitValueTest()
            Truth.assertThat(newThingToDo).isEqualTo(oldThingToDo)
        }
    }

    @Test
    fun submitValidCheckedStatus_dbUpdateIsDone() {

        val navController = Mockito.mock(NavController::class.java)
        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.done_checkbox)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        scenario.onFragment{
            val doneThingsToDo = it.viewModel!!.allThingsDone.getOrAwaitValueTest()
            Truth.assertThat(doneThingsToDo).isNotEmpty()
        }
    }


    @Test
    fun submitValidInput_navigateToToDoFragment() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        val scenario = launchFragmentInContainer<EditToDoFragment>(
            factory = androidTestFragmentFactory,
            themeResId = R.style.Theme_ThingsToDo,
            initialState = Lifecycle.State.CREATED,
            fragmentArgs = bundleOf(EditToDoFragment.ID to 0)
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.editToDoFragment)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.edit_to_do_fab)).perform(
            ViewActions.click()
        )

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.toDoFragment)
    }
}