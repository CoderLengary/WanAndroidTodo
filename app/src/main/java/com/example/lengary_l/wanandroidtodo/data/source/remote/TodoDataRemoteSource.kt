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

package com.example.lengary_l.wanandroidtodo.data.source.remote

import com.example.lengary_l.wanandroidtodo.data.Status
import com.example.lengary_l.wanandroidtodo.data.TodoData
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData
import com.example.lengary_l.wanandroidtodo.data.source.RemoteException
import com.example.lengary_l.wanandroidtodo.data.source.Result
import com.example.lengary_l.wanandroidtodo.data.source.TodoDataSource
import com.example.lengary_l.wanandroidtodo.retrofit.RetrofitClient
import com.example.lengary_l.wanandroidtodo.retrofit.RetrofitService
import com.example.lengary_l.wanandroidtodo.util.AppExecutors
import kotlinx.coroutines.experimental.withContext

/**
 * Created by CoderLengary
 */
class TodoDataRemoteSource private constructor(
        private val mAppExecutors: AppExecutors): TodoDataSource {
    override suspend fun deleteItem(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getLocalDataByDate(dateStr: String): Result<List<TodoDetailData>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        private var INSTANCE: TodoDataRemoteSource? = null

        fun getInstance(mAppExecutors: AppExecutors): TodoDataRemoteSource {
            if (INSTANCE == null){
                synchronized(TodoDataRemoteSource::class.java){
                    if (INSTANCE == null){
                        INSTANCE = TodoDataRemoteSource(mAppExecutors)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    private val mTodoService:RetrofitService.TodoService by lazy {
        val retrofit = RetrofitClient.getInstance()
        retrofit.create(RetrofitService.TodoService::class.java)
    }

    override suspend fun getRemoteDataByType(type: Int): Result<TodoData> = withContext(mAppExecutors.ioContext){
        try {
            val response = mTodoService.getAllListByType(type).execute()

            if (response.isSuccessful) {
                response.body()?.let {
                    //We do not need the error data
                    if (it.errorCode == 0) {
                        Result.Success(it)
                    }else {
                        Result.Error(RemoteException(it.errorMsg))
                    }
                } ?: run {
                    Result.Error(RemoteException())
                }
            }else {
                Result.Error(RemoteException())
            }
        } catch (e: Exception) {
            Result.Error(RemoteException())
        }
    }

    override suspend fun submitItem(title: String, content: String, date: String, type: Int): Result<Status> = withContext(mAppExecutors.ioContext) {
        try {
            val response = mTodoService.submitTodo(title, content, date, type).execute()
            if (response.isSuccessful) {
                response.body()?.let {

                    if (it.errorCode == 0) {
                        Result.Success(it)
                    } else {
                        Result.Error(RemoteException(it.errorMsg))
                    }

                } ?: run{
                    Result.Error(RemoteException())
                }
            }else {
                Result.Error(RemoteException())
            }
        }catch (e: Exception) {
            Result.Error(RemoteException())
        }
    }

    override suspend fun updateItem(id: Int, title: String, content: String, date: String, status: Int, type: Int): Result<Status> = withContext(mAppExecutors.ioContext) {
        try {
            val response = mTodoService.updateTodo(id, title, content, date, status, type).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.errorCode == 0) {
                        Result.Success(it)
                    }else {
                        Result.Error(RemoteException(it.errorMsg))
                    }
                }?: run {
                    Result.Error(RemoteException())
                }
            }else {
                Result.Error(RemoteException())
            }
        }catch (e: Exception) {
            Result.Error(RemoteException())
        }
    }

    override suspend fun deleteItemByRemote(id: Int): Result<Status> = withContext(mAppExecutors.ioContext) {
        try {
            val response = mTodoService.deleteTodo(id).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.errorCode == 0) {
                        Result.Success(it)
                    }else {
                        Result.Error(RemoteException(it.errorMsg))
                    }
                }?: run {
                    Result.Error(RemoteException())
                }
            }else {
                Result.Error(RemoteException())
            }
        }catch (e: Exception) {
            Result.Error(RemoteException())
        }
    }



    override suspend fun insertItem(data: TodoDetailData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override suspend fun clearAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override suspend fun saveAll(data: TodoData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}