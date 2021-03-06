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

package com.example.lengary_l.wanandroidtodo.room.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by CoderLengary
 */
class IntTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun intListToString(ints: List<Int>): String = Gson().toJson(ints)

        @TypeConverter
        @JvmStatic
        fun stringToIntList(string: String): List<Int>? {
            val listType = object : TypeToken<List<Int>>() {}.type
            return Gson().fromJson<List<Int>>(string, listType)
        }
    }
}