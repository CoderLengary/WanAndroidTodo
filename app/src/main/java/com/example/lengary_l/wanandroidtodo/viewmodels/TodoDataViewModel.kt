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
        const val SUCCESS_MSG = "Success!"
        private var INSTANCE: TodoDataViewModel ?= null
        fun getInstance(mRepository: TodoDataRepository): TodoDataViewModel {
            if (INSTANCE == null) {
                INSTANCE = TodoDataViewModel(mRepository)
            }
            return INSTANCE as TodoDataViewModel
        }
    }

    var i = 0

    //Get
    val getAllTodoError = MutableLiveData<Int>()

    val liveTodoList = MutableLiveData<List<TodoDetailData>>()
    val doneTodoList = MutableLiveData<List<TodoDetailData>>()
    val liveTodoListError = MutableLiveData<Int>()
    val doneTodoListError = MutableLiveData<Int>()

    //Submit
    val addStatusData = MutableLiveData<String>()
    val updateType = MutableLiveData<TodoListType>()

    val pointDateMap = HashMap<String, Int>()

    fun changeTodoDataByDate(dateStr: String) {

        launchSilent(uiContext) {
            val liveResult = mRepository.getAllByDateAndStatus(dateStr, 0)
            if (liveResult is Result.Success) {
                liveTodoList.value = liveResult.data
            }else {
                liveTodoListError.value = i++
            }

            val doneResult = mRepository.getAllByDateAndStatus(dateStr, 1)
            if (doneResult is Result.Success) {
                doneTodoList.value = doneResult.data
            }else {
                Log.e("status = 1","error currentdate is "+dateStr)
                doneTodoListError.value = i++
            }
        }
    }




    private fun getTodoDataByType(type: TodoListType) {

        launchSilent(uiContext) {

            val result = mRepository.getAllListByType(type.value)
            if (result is Result.Success) {

                val liveList = result.data.data.allLiveList
                for (todoListData in liveList) {
                    for (todoDetailData in todoListData.list) {
                        val dateStr = todoDetailData.dateStr
                        if (pointDateMap.containsKey(dateStr)) {
                            pointDateMap[dateStr] = pointDateMap[dateStr]!! + 1
                        }else {
                            pointDateMap[dateStr] = 1
                        }
                    }
                }

                val doneList = result.data.data.allDoneList
                for (todoListData in doneList) {
                    for (todoDetailData in todoListData.list) {
                        val dateStr = todoDetailData.dateStr
                        if (pointDateMap.containsKey(dateStr)) {
                            pointDateMap[dateStr] = pointDateMap[dateStr]!! + 1
                        }else {
                            pointDateMap[dateStr] = 1
                        }
                    }
                }


            }
            if (result is Result.Error) {
                getAllTodoError.value = i++
            }
        }
    }

    fun getAllByRemote() {
        getTodoDataByType(TodoListType.LOVE)
        getTodoDataByType(TodoListType.STUDY)
        getTodoDataByType(TodoListType.WORK)
        getTodoDataByType(TodoListType.LIFE)
    }

    fun clearAll() {
        launchSilent(uiContext) {
            mRepository.clearAll()
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




}