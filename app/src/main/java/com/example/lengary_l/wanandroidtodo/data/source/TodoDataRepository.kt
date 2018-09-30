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

package com.example.lengary_l.wanandroidtodo.data.source

import com.example.lengary_l.wanandroidtodo.data.Status
import com.example.lengary_l.wanandroidtodo.data.TodoData
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData

/**
 * Created by CoderLengary
 */
class TodoDataRepository private constructor(
        private val mRemote: TodoDataSource,
        private val mLocal: TodoDataSource
): TodoDataSource{


    override suspend fun getAllByDateAndStatus(dateStr: String, status: Int): Result<List<TodoDetailData>> {
        return mLocal.getAllByDateAndStatus(dateStr, status)
    }


    override suspend fun saveTodo(data: TodoData) {
        mLocal.saveTodo(data)
    }


    companion object {
        private var INSTANCE: TodoDataRepository? =null

        fun getInstance(mRemote: TodoDataSource, mLocal: TodoDataSource): TodoDataRepository{
            if (INSTANCE == null) {
                synchronized(TodoDataRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = TodoDataRepository(mRemote, mLocal)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override suspend fun getAllListByType(type: Int): Result<TodoData> {
        val result =  mRemote.getAllListByType(type)
        if (result is Result.Success) {
            saveTodo(result.data)
        }
        return result
    }

    override suspend fun submitTodo(title: String, content: String, date: String, type: Int): Result<Status> {

        val result = mRemote.submitTodo(title, content, date, type)
        if (result is Result.Success) {
            insertTodoDetailData(result.data.data)
        }
        return result
    }

    override suspend fun updateTodo(id: Int, title: String, content: String, date: String, status: Int, type: Int): Result<Status> {
        val result = mRemote.updateTodo(id, title, content, date, status, type)
        if (result is Result.Success) {
            insertTodoDetailData(result.data.data)
        }
        return result
    }

    override suspend fun insertTodoDetailData(data: TodoDetailData) {
        mLocal.insertTodoDetailData(data)
    }

    override suspend fun deleteTodo(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun clearAll() {
        mLocal.clearAll()
    }
}