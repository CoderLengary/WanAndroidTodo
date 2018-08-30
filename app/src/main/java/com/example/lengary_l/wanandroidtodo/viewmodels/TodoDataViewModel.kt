/*
 * Copyright (c) 2018 CoderLengary
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.lengary_l.wanandroidtodo.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData
import com.example.lengary_l.wanandroidtodo.data.TodoListType
import com.example.lengary_l.wanandroidtodo.data.source.Result
import com.example.lengary_l.wanandroidtodo.data.source.TodoDataRepository
import com.example.lengary_l.wanandroidtodo.util.launchSilent
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by CoderLengary
 */
class TodoDataViewModel private constructor(
        private val mRepository: TodoDataRepository,
        private val uiContext: CoroutineContext = UI
): ViewModel() {

    companion object {
        private var INSTANCE: TodoDataViewModel ?= null
        fun getInstance(mRepository: TodoDataRepository): TodoDataViewModel {
            if (INSTANCE == null) {
                INSTANCE = TodoDataViewModel(mRepository)
            }
            return INSTANCE as TodoDataViewModel
        }
    }

    val liveTodoData = MutableLiveData<List<TodoDetailData>>()
    val doneTodoData = MutableLiveData<List<TodoDetailData>>()




    fun changeTodoDataByType(type: TodoListType) {

        launchSilent(uiContext) {
            val liveTodoList = ArrayList<TodoDetailData>()
            val doneTodoList = ArrayList<TodoDetailData>()
            val result = mRepository.getAllListByType(type.value)
            if (result is Result.Success) {
                result.data.data.allLiveList.forEach {
                    liveTodoList.addAll(it.list)
                }

                result.data.data.allDoneList.forEach {
                    doneTodoList .addAll(it.list)
                }
                if (!liveTodoList.isEmpty()) {
                    liveTodoData.value = liveTodoList
                }
                if (!doneTodoList.isEmpty()) {
                    doneTodoData.value = doneTodoList
                }
            } else if(result is Result.Error) {
                Log.e("errror", result.exception.message)
            }
        }
    }



}