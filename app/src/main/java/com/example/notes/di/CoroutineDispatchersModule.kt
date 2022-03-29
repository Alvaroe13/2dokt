package com.example.notes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchersModule {

    @Provides
    @Singleton
    @Named("IO")
    fun provideDispatcherIO() : CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    @Named("Main")
    fun provideDispatcherMain() : CoroutineDispatcher {
        return Dispatchers.IO
    }

}