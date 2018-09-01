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

/**
 * Created by CoderLengary
 */
class TodoDataRepository private constructor(
        private val mRemote: TodoDataSource
): TodoDataSource{



    companion object {
        private var INSTANCE: TodoDataRepository? =null

        fun getInstance(mRemote: TodoDataSource): TodoDataRepository{
            if (INSTANCE == null) {
                synchronized(TodoDataRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = TodoDataRepository(mRemote)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override suspend fun getAllListByType(type: Int): Result<TodoData> {
        return mRemote.getAllListByType(type)

    }

    override suspend fun submitTodo(title: String, content: String, date: String, type: Int): Result<Status> {
        return mRemote.submitTodo(title, content, date, type)
    }

    override suspend fun updateTodo(id: Int, title: String, content: String, date: String, status: Int, type: Int): Result<Status> {
        return mRemote.updateTodo(id, title, content, date, status, type)
    }
}