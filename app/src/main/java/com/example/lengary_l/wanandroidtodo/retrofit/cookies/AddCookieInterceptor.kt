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
class AddCookieInterceptor: Interceptor {

    companion object {
        private const val COOKIE_NAME = "Cookie"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val host = request.url().host()
        val builder = request.newBuilder()
        val userId = SharedPreferencesUtils.getUserId()
        if (host.isNotEmpty() && userId != -1) {
            val cookiesString = SharedPreferencesUtils.getCookies(host)
            Log.e("Interceptor", cookiesString)
            if (cookiesString.isNotEmpty() ) {
                builder.addHeader(COOKIE_NAME, cookiesString)
            }
        }

        return chain.proceed(builder.build())

    }
}