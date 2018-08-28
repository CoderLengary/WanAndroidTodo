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

package com.example.lengary_l.wanandroidtodo.retrofit.cookies

import android.util.Log
import com.example.lengary_l.wanandroidtodo.util.SharedPreferencesUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by CoderLengary
 */
class GetCookieInterceptor: Interceptor {

    //Only in the case of login and registration,
    // we consider storing the cookies
    companion object {
        private const val KEY_LOGIN = "user/login"
        private const val KEY_REGISTER = "user/register"
        private const val KEY_COOKIES = "Set-Cookie"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        val host = request.url().host()


        if ((requestUrl.contains(KEY_LOGIN)||requestUrl.contains(KEY_REGISTER))
                        && !response.headers(KEY_COOKIES).isEmpty())  {

            val cookies = response.headers(KEY_COOKIES)
            val cookiesString = encodeCookies(cookies)
            saveCookies(host, cookiesString)
        }
        return response
    }

    //In order to put the cookies into SharedPreferences, We change the list to String
    private fun encodeCookies(cookies: List<String>): String {

        val sb = StringBuilder()
        val set = HashSet<String>()
        /*
        Sample: cookies: list--("loginUserName=name; Expires=Thu; Path=/"  ,  "loginUserPassword=password; Expires=Thu; Path=/")
                cookies.map: list--("loginUserName=name", "Expires=Thu", "Path=/", "loginUserPassword=password", "Expires=Thu", "Path=/" )
         */
        cookies.map {
            cookie -> cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }.forEach {
            it.filterNot { set.contains(it) }.forEach { set.add(it) }
        }

        val iterator = set.iterator()
        while (iterator.hasNext()) {
            val cookie = iterator.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        Log.e("Interceptor", sb.toString())
        return sb.toString()
    }

    private fun saveCookies(host: String, cookiesString: String) {
        SharedPreferencesUtils.putCookies(host, cookiesString)
    }
}