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

package com.example.lengary_l.wanandroidtodo.injection

import android.content.Context
import com.example.lengary_l.wanandroidtodo.data.source.LoginDataRepository
import com.example.lengary_l.wanandroidtodo.data.source.TodoDataRepository
import com.example.lengary_l.wanandroidtodo.data.source.local.LoginDataLocalSource
import com.example.lengary_l.wanandroidtodo.data.source.local.TodoDataLocalSource
import com.example.lengary_l.wanandroidtodo.data.source.remote.LoginDataRemoteSource
import com.example.lengary_l.wanandroidtodo.data.source.remote.TodoDataRemoteSource
import com.example.lengary_l.wanandroidtodo.room.AppDatabase
import com.example.lengary_l.wanandroidtodo.util.AppExecutors
import com.example.lengary_l.wanandroidtodo.viewmodels.LoginDataViewModelFactory
import com.example.lengary_l.wanandroidtodo.viewmodels.TodoDataViewModelFactory

/**
 * Created by CoderLengary
 */
object Injection {
    private val mAppExecutors = AppExecutors()

    private fun getLoginDataRepository(context: Context): LoginDataRepository =
            LoginDataRepository.getInstance(LoginDataRemoteSource.getInstance(mAppExecutors), LoginDataLocalSource.getInstance(mAppExecutors, AppDatabase.getInstance(context).loginDetailDataDao()))

    private fun getTodoDataRepository(context: Context): TodoDataRepository =
            TodoDataRepository.getInstance(TodoDataRemoteSource.getInstance(mAppExecutors),
                    TodoDataLocalSource.getInstance(mAppExecutors, AppDatabase.getInstance(context).todoDetailDataDao()))

    fun provideLoginDataViewModelFactory(context: Context): LoginDataViewModelFactory {
        val mLoginDataRepository = getLoginDataRepository(context)
        return LoginDataViewModelFactory(mLoginDataRepository)
    }

    fun provideTodoDataViewModelFactory(context: Context): TodoDataViewModelFactory {
        val mTodoDataRepository = getTodoDataRepository(context)
        return TodoDataViewModelFactory(mTodoDataRepository)
    }
}