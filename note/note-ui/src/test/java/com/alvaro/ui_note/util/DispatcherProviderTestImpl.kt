package com.alvaro.ui_note.util

import com.alvaro.core.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class DispatcherProviderTestImpl: DispatcherProvider {

    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    override fun io(): CoroutineDispatcher {
        return testDispatcher
    }

    override fun main(): CoroutineDispatcher {
        return testDispatcher
    }

    override fun default(): CoroutineDispatcher {
        return testDispatcher
    }
}