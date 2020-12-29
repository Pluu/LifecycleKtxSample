package com.pluu.sample.coroutinektx

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import timber.log.Timber

class SampleViewModel : ViewModel() {
    val flowToLiveDataCounter: LiveData<Int> = flow {
        var value = 0
        while (true) {
            value++
            Timber.tag("ViewModel").d("FlowToLiveData : $value")
            emit(value)
            delay(100L)
        }
    }.asLiveData()

    val flowCounter: Flow<Int> = flow {
        var value = 0
        while (true) {
            value++
            Timber.tag("ViewModel").d("Flow : $value")
            emit(value)
            delay(100L)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val channelFlowCounter = channelFlow {
        var value = 0
        while (isActive) {
            value++
            Timber.tag("ViewModel").d("ChannelFlow : $value")
            offer(value)
            delay(100L)
        }
    }
}