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

package com.example.lengary_l.wanandroidtodo.room.dao

import android.arch.persistence.room.*
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData

/**
 * Created by CoderLengary
 */
@Dao
interface TodoDetailDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodoDetailData(data: TodoDetailData)

    @Query("SELECT * FROM todo WHERE dateStr = :dateStr")
    fun queryAllByDate(dateStr: String): List<TodoDetailData>

    @Query("SELECT * FROM todo")
    fun queryAll(): List<TodoDetailData>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun queryTodoDetailDataById(id: Int):TodoDetailData?

    @Delete
    fun deleteTodoDetailData(item: TodoDetailData)

    @Delete
    fun deleteAll(list: List<TodoDetailData>)


}