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
import com.example.lengary_l.wanandroidtodo.data.TodoListData
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
        const val SUCCESS_MSG = "Success!"
        private var INSTANCE: TodoDataViewModel ?= null
        fun getInstance(mRepository: TodoDataRepository): TodoDataViewModel {
            if (INSTANCE == null) {
                INSTANCE = TodoDataViewModel(mRepository)
            }
            return INSTANCE as TodoDataViewModel
        }
    }

    //Get
    val liveTodoData = MutableLiveData<List<TodoListData>>()
    val doneTodoData = MutableLiveData<List<TodoListData>>()

    //Submit
    val addStatusData = MutableLiveData<String>()
    val updateType = MutableLiveData<TodoListType>()

    //Update
    val updateStatusData = MutableLiveData<String>()


    fun changeTodoDataByType(type: TodoListType) {

        launchSilent(uiContext) {

            val result = mRepository.getAllListByType(type.value)
            if (result is Result.Success) {
                liveTodoData.value = result.data.data.allLiveList

                doneTodoData.value = result.data.data.allDoneList


            } else if(result is Result.Error) {
                Log.e("error", result.exception.message)
            }
        }
    }

    fun submitTodo(title: String, content: String, date: String, type: TodoListType) {

        launchSilent(uiContext) {
            val result = mRepository.submitTodo(title, content, date, type.value)

            if (result is Result.Success) {
                addStatusData.value = SUCCESS_MSG

                //When we add an todo_data successfully, update the {addType}, one of the observers{ @link HomeFragment } will receive it.
                updateType.value = type
            }else if (result is Result.Error) {
                addStatusData.value = result.exception.message
            }
        }

    }

    fun clearStatusData() {
        addStatusData.value = null
    }

    fun updateTodo(id: Int, title: String, content: String, date: String, status: Int, type: TodoListType) {

        launchSilent(uiContext) {
            val result = mRepository.updateTodo(id, title, content, date, status, type.value)
            if (result is Result.Success) {
                updateStatusData.value = SUCCESS_MSG

                //When we complete a todo_data, update the {addType}, one of the observers{ @link HomeFragment } will receive it.
                updateType.value = type
            }else if (result is Result.Error) {
                updateStatusData.value = result.exception.message
            }
        }
    }



}