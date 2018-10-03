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

package com.example.lengary_l.wanandroidtodo.data.source.local

import com.example.lengary_l.wanandroidtodo.data.Status
import com.example.lengary_l.wanandroidtodo.data.TodoData
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData
import com.example.lengary_l.wanandroidtodo.data.source.RemoteException
import com.example.lengary_l.wanandroidtodo.data.source.Result
import com.example.lengary_l.wanandroidtodo.data.source.TodoDataSource
import com.example.lengary_l.wanandroidtodo.room.dao.TodoDetailDataDao
import com.example.lengary_l.wanandroidtodo.util.AppExecutors
import kotlinx.coroutines.experimental.withContext

/**
 * Created by CoderLengary
 */
class TodoDataLocalSource private constructor(
        private val mAppExecutors: AppExecutors,
        private val mTodoDetailDataDao: TodoDetailDataDao): TodoDataSource {
    override suspend fun getLocalDataByDate(dateStr: String): Result<List<TodoDetailData>> =
            withContext(mAppExecutors.ioContext) {
                val result = mTodoDetailDataDao.queryAllByDate(dateStr)
                if (!result.isEmpty()) Result.Success(result) else Result.Error(RemoteException())
            }


    override suspend fun insertItem(data: TodoDetailData) {
        withContext(mAppExecutors.ioContext) {
            mTodoDetailDataDao.insertTodoDetailData(data)
        }
    }




    override suspend fun clearAll() {
         withContext(mAppExecutors.ioContext) {
             val result = mTodoDetailDataDao.queryAll()
             if (!result.isEmpty()) mTodoDetailDataDao.deleteAll(result)
         }
    }

    override suspend fun deleteItem(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        private var INSTANCE:TodoDataLocalSource ?= null

        fun getInstance(mAppExecutors: AppExecutors, mTodoDetailDataDao: TodoDetailDataDao): TodoDataLocalSource  {
            if (INSTANCE == null) {
                synchronized(TodoDataLocalSource::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = TodoDataLocalSource(mAppExecutors, mTodoDetailDataDao)
                    }
                }
            }
            return INSTANCE!!
        }
    }


    override suspend fun saveAll(data: TodoData) {
        withContext(mAppExecutors.ioContext) {
            val incompleteList = data.data.incompleteList

            for (todoListData in incompleteList) {
                for (todoDetailData in todoListData.list) {
                    mTodoDetailDataDao.insertTodoDetailData(todoDetailData)
                }
            }
            val completeList = data.data.completeList

            for (doneListData in completeList) {
                for (todoDetailData in doneListData.list) {
                    mTodoDetailDataDao.insertTodoDetailData(todoDetailData)
                }
            }

        }
    }

    override suspend fun getRemoteDataByType(type: Int): Result<TodoData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun submitItem(title: String, content: String, date: String, type: Int): Result<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateItem(id: Int, title: String, content: String, date: String, status: Int, type: Int): Result<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}