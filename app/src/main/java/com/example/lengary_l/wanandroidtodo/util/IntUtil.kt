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

import com.example.lengary_l.wanandroidtodo.data.TodoListType

/**
 * Created by CoderLengary
 */
fun Int.changeToListType(): TodoListType =
    when(this) {
        TodoListType.LOVE.value -> TodoListType.LOVE
        TodoListType.WORK.value -> TodoListType.WORK
        TodoListType.LIFE.value -> TodoListType.LIFE
        TodoListType.STUDY.value -> TodoListType.STUDY
        else -> TodoListType.LOVE
    }


