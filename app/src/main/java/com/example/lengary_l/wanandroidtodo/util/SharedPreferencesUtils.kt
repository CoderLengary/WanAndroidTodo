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
import android.content.SharedPreferences
import com.example.lengary_l.wanandroidtodo.app.App

/**
 * Created by CoderLengary
 */
object SharedPreferencesUtils {

    private const val USER_PREF = "uerPref"

    private val userPref: SharedPreferences? by lazy {
        App.instance?.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
    }

    private val userPrefEditor: SharedPreferences.Editor by lazy {
        userPref!!.edit()
    }

    fun putCookies(host: String, cookies: String) {
        userPrefEditor.putString(host, cookies)
        userPrefEditor.apply()
    }

    fun getCookies(host: String): String {
        return userPref!!.getString(host, "")
    }

    fun putUserId(key: String = "USER_ID", id: Int) {
        userPrefEditor.putInt(key, id)
        userPrefEditor.apply()
    }

    fun getUserId(key: String = "USER_ID"): Int {
        return userPref!!.getInt(key, -1)
    }

}