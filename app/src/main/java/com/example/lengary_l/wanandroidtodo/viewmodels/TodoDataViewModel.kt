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
        const val ADD_SUCCESS_MSG = "Add Success!"
        const val UPDATE_SUCCESS_MSG= "Update Success!"
        const val DELETE_SUCCESS_MSG = "Delete Success!"
        const val ERROR = "ERROR"
        const val ERROR_CODE = -1
        private var INSTANCE: TodoDataViewModel ?= null
        fun getInstance(mRepository: TodoDataRepository): TodoDataViewModel {
            if (INSTANCE == null) {
                INSTANCE = TodoDataViewModel(mRepository)
            }
            return INSTANCE as TodoDataViewModel
        }
    }



    //Get
    val getAllRemoteTodoError = MutableLiveData<Int>()
    val localTodoListByDate = MutableLiveData<List<TodoDetailData>>()



    //Submit/Update/Delete
    val statusData = MutableLiveData<String>()

    //We will mark all the dates whose todoList is not empty
    val markDateMap = HashMap<String, Int>()


    fun getLocalTodoDataByDate(dateStr: String) {
        launchSilent(uiContext) {
            val result = mRepository.getLocalDataByDate(dateStr)
            if (result is Result.Success) {
                localTodoListByDate.value = result.data
            }else {
                localTodoListByDate.value = null
            }
        }
    }



    fun getAllRemoteTodoData() {
        getRemoteDataByType(TodoListType.LOVE)
        getRemoteDataByType(TodoListType.STUDY)
        getRemoteDataByType(TodoListType.WORK)
        getRemoteDataByType(TodoListType.LIFE)
    }
    private fun getRemoteDataByType(type: TodoListType) {

        launchSilent(uiContext) {
            val result = mRepository.getRemoteDataByType(type.value)
            if (result is Result.Success) {

                val incompleteList = result.data.data.incompleteList
                for (todoListData in incompleteList) {
                    for (todoDetailData in todoListData.list) {
                        val dateStr = todoDetailData.dateStr
                        if (markDateMap.containsKey(dateStr)) {
                            markDateMap[dateStr] = markDateMap[dateStr]!! + 1
                        }else {
                            markDateMap[dateStr] = 1
                        }
                    }
                }

                val completeList = result.data.data.completeList
                for (todoListData in completeList) {
                    for (todoDetailData in todoListData.list) {
                        val dateStr = todoDetailData.dateStr
                        if (markDateMap.containsKey(dateStr)) {
                            markDateMap[dateStr] = markDateMap[dateStr]!! + 1
                        }else {
                            markDateMap[dateStr] = 1
                        }
                    }
                }

            }
            if (result is Result.Error) {
                getAllRemoteTodoError.value = ERROR_CODE
            }
        }
    }
    fun clearAllTodo() {
        launchSilent(uiContext) {
            mRepository.clearAll()
            markDateMap.clear()
        }
    }


    fun submitTodo(title: String, content: String, date: String, type: TodoListType) {
        if (markDateMap.containsKey(date)) {
            markDateMap[date] = markDateMap[date]!! + 1
        }else {
            markDateMap[date] = 1
        }
        //Actually, if we submit the todoData successfully, the data will be inserted into database
        launchSilent(uiContext) {
            val result = mRepository.submitItem(title, content, date, type.value)

            if (result is Result.Success) {
                statusData.value = ADD_SUCCESS_MSG
            }else  {
                statusData.value = ERROR
            }
        }

    }



    fun updateTodo(id: Int, title: String, content: String, date: String, status: Int, type: Int) {
        launchSilent(uiContext) {
            val result = mRepository.updateItem(id, title, content, date, status, type)
            if (result is Result.Success) {
                statusData.value = UPDATE_SUCCESS_MSG
            }else  {
                statusData.value = ERROR
            }

        }
    }

    fun deleteTodo(id: Int, dateStr: String) {

        if (markDateMap.containsKey(dateStr)) {
            markDateMap[dateStr] = markDateMap[dateStr]!! - 1
        }
        launchSilent(uiContext) {
            val result = mRepository.deleteItemByRemote(id)
            if (result is Result.Success) {
                statusData.value = DELETE_SUCCESS_MSG
            }else  {
                statusData.value = ERROR
            }
        }
    }


}