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
import com.example.lengary_l.wanandroidtodo.data.LoginData
import com.example.lengary_l.wanandroidtodo.data.PostType
import com.example.lengary_l.wanandroidtodo.data.source.LoginDataRepository
import com.example.lengary_l.wanandroidtodo.data.source.Result
import com.example.lengary_l.wanandroidtodo.util.launchSilent
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by CoderLengary
 */
class LoginDataViewModel private constructor(
        private val loginDataRepository: LoginDataRepository,

        private val uiContext: CoroutineContext = UI
): ViewModel(){

    companion object {
        private var INSTANCE: LoginDataViewModel ?= null
        fun getInstance(loginDataRepository: LoginDataRepository): LoginDataViewModel {
            if (INSTANCE == null) {
                INSTANCE = LoginDataViewModel(loginDataRepository)
            }
            return INSTANCE as LoginDataViewModel
        }
    }

    val loginData = MutableLiveData<LoginData>()

    val registerData = MutableLiveData<LoginData>()

    val loginExceptionData = MutableLiveData<String>()

    val registerExceptionData = MutableLiveData<String>()

    fun setInput(userName: String, password: String, type: PostType){
        launchSilent(uiContext) {
            val result = loginDataRepository.getRemoteLoginData(userName, password, type)

            when(type) {
                PostType.TYPE_LOGIN -> if (result is Result.Success) {
                    loginData.value = result.data
                }else if (result is Result.Error){
                    loginExceptionData.value = result.exception.message
                }

                PostType.TYPE_REGISTER -> if (result is Result.Success) {
                    registerData.value = result.data
                }else if (result is Result.Error){
                    registerExceptionData.value = result.exception.message
                }
            }

        }
    }






}