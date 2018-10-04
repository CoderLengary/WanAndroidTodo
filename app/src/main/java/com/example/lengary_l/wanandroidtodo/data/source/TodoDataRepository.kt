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
    override suspend fun deleteItemByRemote(id: Int): Result<Status> {
        val result = mRemote.deleteItemByRemote(id)
        if (result is Result.Success) {
            deleteItem(id)
        }
        return result
    }

    override suspend fun deleteItem(id: Int) {
        mLocal.deleteItem(id)
    }

    override suspend fun getLocalDataByDate(dateStr: String): Result<List<TodoDetailData>> {
        return mLocal.getLocalDataByDate(dateStr)
    }





    override suspend fun saveAll(data: TodoData) {
        mLocal.saveAll(data)
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

    override suspend fun getRemoteDataByType(type: Int): Result<TodoData> {
        val result =  mRemote.getRemoteDataByType(type)
        if (result is Result.Success) {
            saveAll(result.data)
        }
        return result
    }

    override suspend fun submitItem(title: String, content: String, date: String, type: Int): Result<Status> {

        val result = mRemote.submitItem(title, content, date, type)
        if (result is Result.Success) {
            insertItem(result.data.data)
        }
        return result
    }

    override suspend fun updateItem(id: Int, title: String, content: String, date: String, status: Int, type: Int): Result<Status> {
        val result = mRemote.updateItem(id, title, content, date, status, type)
        if (result is Result.Success) {
            insertItem(result.data.data)
        }
        return result
    }

    override suspend fun insertItem(data: TodoDetailData) {
        mLocal.insertItem(data)
    }



    override suspend fun clearAll() {
        mLocal.clearAll()
    }
}