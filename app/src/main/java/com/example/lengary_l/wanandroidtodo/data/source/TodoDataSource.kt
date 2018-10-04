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

package com.example.lengary_l.wanandroidtodo.data.source

import com.example.lengary_l.wanandroidtodo.data.Status
import com.example.lengary_l.wanandroidtodo.data.TodoData
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData

/**
 * Created by CoderLengary
 */
interface TodoDataSource {

    suspend fun getRemoteDataByType(type: Int): Result<TodoData>

    suspend fun submitItem(title: String, content: String, date: String, type: Int): Result<Status>

    suspend fun updateItem(id: Int, title: String, content: String, date: String, status: Int, type: Int): Result<Status>

    suspend fun saveAll(data: TodoData)

    suspend fun insertItem(data: TodoDetailData)

    suspend fun deleteItemByRemote(id: Int): Result<Status>

    suspend fun deleteItem(id: Int)

    suspend fun clearAll()

    suspend fun getLocalDataByDate(dateStr: String): Result<List<TodoDetailData>>


}