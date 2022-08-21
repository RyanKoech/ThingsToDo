package com.example.thingstodo.viewmodel

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.thingstodo.MainCoroutineRule
import com.example.thingstodo.getOrAwaitValueTest
import com.example.thingstodo.other.Constants
import com.example.thingstodo.other.Status
import com.example.thingstodo.receiver.ThingToDoReceiver
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

    // TODO: Test Setting and Cancelling of Alarms upon database insertions, updates and deletions.
}