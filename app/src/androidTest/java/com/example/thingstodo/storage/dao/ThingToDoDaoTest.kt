package com.example.thingstodo.storage.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.thingstodo.getOrAwaitValueTest
import com.example.thingstodo.model.ThingToDo
import com.example.thingstodo.storage.database.ThingToDoRoomDatabase
import com.example.thingstodo.utilities.AndroidTestUtilities
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@SmallTest
class ThingToDoDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ThingToDoRoomDatabase
    private lateinit var dao : ThingToDoDao
    private lateinit var thingToDo : ThingToDo
    private var id by Delegates.notNull<Int>()

    @Before
    fun setUp() {
        id = 1
        thingToDo = AndroidTestUtilities.getValidThingToDo(id)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(appContext, ThingToDoRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.thingToDoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertThingToDoItem() = runBlockingTest {
        dao.insertThingToDo(thingToDo)

        val allThingsToDo = dao.observeThingsToDo().asLiveData().getOrAwaitValueTest()

        assertThat(allThingsToDo).contains(thingToDo)

    }

    @Test
    fun deleteThingToDoItem() = runBlockingTest {
        dao.insertThingToDo(thingToDo)
        dao.deleteThingToDo(thingToDo)

        val allThingsToDo = dao.observeThingsToDo().asLiveData().getOrAwaitValueTest()

        assertThat(allThingsToDo).isEmpty()
    }

    @Test
    fun updateThingToDoItem() = runBlockingTest {
        dao.insertThingToDo(thingToDo)
        thingToDo = AndroidTestUtilities.getValidThingToDo(
            id = 1,
            done = true
        )
        dao.updateThingToDo(thingToDo)

        val allThingsToDo = dao.observeThingsDone().asLiveData().getOrAwaitValueTest()

        assertThat(allThingsToDo).contains(thingToDo)
    }

    @Test
    fun observeThingToDoItem() = runBlockingTest {
        dao.insertThingToDo(thingToDo)

        val observedThingToDo = dao.observeThingToDo(id).asLiveData().getOrAwaitValueTest()

        assertThat(observedThingToDo).isEqualTo(thingToDo)
    }
}