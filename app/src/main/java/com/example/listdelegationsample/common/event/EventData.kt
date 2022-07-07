package com.example.listdelegationsample.common.event

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class EventData<T>(
    private val coroutineScope: CoroutineScope,
    private val eventDispatcher: CoroutineDispatcher,
    val onEvent: (T) -> Unit,
    private val exception: ((Throwable) -> Unit)? = null
) {

    private val channel = Channel<T>()

    init {
        coroutineScope.launch {
            channel.consumeEach {
                launch(eventDispatcher) {
                    if (exception != null) {
                        try {
                            onEvent(it)
                        } catch (e: Exception) {
                            exception.invoke(e)
                        }
                    } else {
                        onEvent(it)
                    }
                }
            }
        }
    }

    fun postEvent(event: Any) {
        if (!channel.isClosedForSend) {
            coroutineScope.launch {
                channel.send(event as T)
            }
        } else {
            println("Channel is closed for send")
        }
    }

    fun cancel() {
        channel.cancel()
    }
}