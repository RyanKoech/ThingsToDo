package com.example.thingstodo.di

import android.content.Context
import androidx.room.Room
import com.example.thingstodo.other.Constants.DATABASE_NAME
import com.example.thingstodo.repository.DefaultThingToDoRepository
import com.example.thingstodo.repository.ThingToDoRepository
import com.example.thingstodo.storage.dao.ThingToDoDao
import com.example.thingstodo.storage.database.ThingToDoRoomDatabase
import com.example.thingstodo.utilities.ContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideThingDoToDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ThingToDoRoomDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideThingToDoDao(
        database: ThingToDoRoomDatabase
    ) = database.thingToDoDao()

    @Provides
    @Singleton
    fun provideContextProvider(@ApplicationContext context: Context): ContextProvider {
        return object : ContextProvider {
            override fun getContext(): Context {
                return context
            }

        }
    }

    @Provides
    @Singleton
    fun provideDefaultThingToDoRepository(
        thingToDoDao: ThingToDoDao
    ) = DefaultThingToDoRepository(thingToDoDao) as ThingToDoRepository

}