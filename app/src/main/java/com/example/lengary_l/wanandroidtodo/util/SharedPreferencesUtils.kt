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

package com.example.lengary_l.wanandroidtodo.util

import android.content.Context
import com.example.lengary_l.wanandroidtodo.app.App

/**
 * Created by CoderLengary
 */
object SharedPreferencesUtils {

    private const val USER_PREF = "uerPref"

    const val USER_ID = "USER_ID"

    const val USER_NAME = "USER_NAME"

    const val USER_PASSWORD = "USER_PASSWORD"

    private const val AUTO_LOGIN = "AUTO_LOGIN"

    private val userPref = App.instance.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)



    fun putCookies(host: String, cookies: String) {
        userPref.edit().putString(host, cookies).apply()
    }

    fun getCookies(host: String): String {
        return userPref.getString(host, "")
    }

    fun putUserId( id: Int) {
        userPref.edit().putInt(USER_ID, id).apply()
    }

    fun getUserId(): Int {
        return userPref.getInt(USER_ID, -1)
    }



    fun putUserName(userName: String) {
        userPref.edit().putString(USER_NAME, userName).apply()
    }

    fun getUserName(): String {
        return userPref.getString(USER_NAME, "")
    }

    fun putUserPassword(userPassword: String) {
        userPref.edit().putString(USER_PASSWORD, userPassword).apply()
    }

    fun getUserPassword():String {
        return userPref.getString(USER_PASSWORD, "")
    }

    fun putAutoLogin(autoLogin: Boolean) {
        userPref.edit().putBoolean(AUTO_LOGIN, autoLogin).apply()
    }

    fun getAutoLogin(): Boolean {
        return userPref.getBoolean(AUTO_LOGIN, false)
    }


}