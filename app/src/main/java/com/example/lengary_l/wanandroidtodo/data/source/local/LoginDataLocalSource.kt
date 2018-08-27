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

import com.example.lengary_l.wanandroidtodo.data.LoginData
import com.example.lengary_l.wanandroidtodo.data.LoginDetailData
import com.example.lengary_l.wanandroidtodo.data.PostType
import com.example.lengary_l.wanandroidtodo.data.source.LoginDataSource
import com.example.lengary_l.wanandroidtodo.data.source.Result
import com.example.lengary_l.wanandroidtodo.room.dao.LoginDetailDataDao
import com.example.lengary_l.wanandroidtodo.util.AppExecutors
import kotlinx.coroutines.experimental.withContext

/**
 * Created by CoderLengary
 */
class LoginDataLocalSource private constructor(
        private val mAppExecutors: AppExecutors,
        private val mLoginDetailDataDao: LoginDetailDataDao): LoginDataSource {

    companion object {
        private var INSTANCE:LoginDataLocalSource ?= null

        fun getInstance(mAppExecutors: AppExecutors,
                        mLoginDetailDataDao: LoginDetailDataDao): LoginDataLocalSource {
            if (INSTANCE == null) {
                synchronized(LoginDataLocalSource::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = LoginDataLocalSource(mAppExecutors, mLoginDetailDataDao)
                    }
                }
            }
            return INSTANCE!!
        }
    }


    override suspend fun getRemoteLoginData(userName: String, password: String, type: PostType): Result<LoginData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveLoginData(data: LoginDetailData) {
        withContext(mAppExecutors.ioContext) {
            mLoginDetailDataDao.insertLoginDetailData(data)
        }
    }

    override suspend fun getLocalLoginData(): Result<LoginData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}