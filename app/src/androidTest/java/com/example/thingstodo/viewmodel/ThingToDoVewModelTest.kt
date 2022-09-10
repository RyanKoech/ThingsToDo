package com.example.thingstodo.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.thingstodo.getOrAwaitValueTest
import com.example.thingstodo.other.Status
import com.example.thingstodo.repository.FakeThingToDoRepository
import com.example.thingstodo.utilities.AndroidTestUtilities
import com.example.thingstodo.utilities.ContextProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
class ThingToDoVewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ThingToDoViewModel

    @Inject
    private lateinit var contextProvider: ContextProvider

    @Before
    fun setUp() {
        contextProvider = object  : ContextProvider {
            override fun getContext(): Context {
                return InstrumentationRegistry.getInstrumentation().targetContext
            }

        }
        viewModel = ThingToDoViewModel(FakeThingToDoRepository(), contextProvider)
    }

    @Test
    fun getNewThingToDoWithBlankName_returnsError() {

        viewModel.getNewThingToDoEntry("", AndroidTestUtilities.validThingToDoDescription, AndroidTestUtilities.validThingToDoDate)
        val value  = viewModel.getNewThingToDoStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun getNewThingToDoWithBlankDescription_returnsError() {

        viewModel.getNewThingToDoEntry(AndroidTestUtilities.validThingToDoName, "", AndroidTestUtilities.validThingToDoDate)
        val value  = viewModel.getNewThingToDoStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun getThingToDo_returnThingToDoFromDb() {
        val newThingToDo = AndroidTestUtilities.getValidThingToDo()
        viewModel.addNewThingToDo(newThingToDo.name, newThingToDo.description, newThingToDo.timeStamp)

        viewModel.allThingsToDo.getOrAwaitValueTest()

        val thingToDo = viewModel.getThingToDo(0).getOrAwaitValueTest()
        assertThat(thingToDo).isEqualTo(newThingToDo)
    }

    @Test
    fun addNewThingToDo_insertsThingToDoInDb() {
        viewModel.addNewThingToDo(AndroidTestUtilities.validThingToDoName, AndroidTestUtilities.validThingToDoDescription, AndroidTestUtilities.validThingToDoDate)

        val thingsToDo = viewModel.allThingsToDo.getOrAwaitValueTest()

        assertThat(thingsToDo).isNotEmpty()
    }

    @Test
    fun updateThingToDo_updatesThingToDoInDb() {
        val newThingToDo = AndroidTestUtilities.getValidThingToDo(
            name = "${AndroidTestUtilities.validThingToDoName}1",
            description = "${AndroidTestUtilities.validThingToDoDescription}1",
            done = true
        )
        viewModel.addNewThingToDo(AndroidTestUtilities.validThingToDoName, AndroidTestUtilities.validThingToDoDescription, AndroidTestUtilities.validThingToDoDate)
        viewModel.updateNewThingToDo(newThingToDo.id, newThingToDo.name, newThingToDo.description, newThingToDo.timeStamp, newThingToDo.done)

        val thingsToDo = viewModel.allThingsDone.getOrAwaitValueTest()
        assertThat(thingsToDo).contains(newThingToDo)
    }

    @Test
    fun deleteThingToDo_deletesThingToDoInDb() {
        val name = AndroidTestUtilities.validThingToDoName
        val description = AndroidTestUtilities.validThingToDoDescription
        val date = AndroidTestUtilities.validThingToDoDate

        viewModel.addNewThingToDo(name, description, date)
        viewModel.deleteThingToDo(0, name, description, date)
        val thingsToDo = viewModel.allThingsToDo.getOrAwaitValueTest()
        assertThat(thingsToDo).isEmpty()
    }

    // TODO: Test Setting and Cancelling of Alarms upon database insertions, updates and deletions.
}