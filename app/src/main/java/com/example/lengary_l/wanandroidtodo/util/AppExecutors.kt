package com.example.lengary_l.wanandroidtodo.util

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by CoderLengary
 */
const val THREAD_COUNT = 3
open class AppExecutors {
    val ioContext: CoroutineContext = DefaultDispatcher
    val networkContext: CoroutineContext = newFixedThreadPoolContext(THREAD_COUNT, "networkIO")
    val uiContext: CoroutineContext = UI
}