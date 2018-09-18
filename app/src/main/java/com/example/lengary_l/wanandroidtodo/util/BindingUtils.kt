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

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoListType

/**
 * Created by CoderLengary
 */

@BindingAdapter("imageFromType")
fun bindImageFromType(view: ImageView, type: Int) {
    when(type) {
        TodoListType.LOVE.value -> view.setImageResource(R.drawable.ic_favorite_white_24dp)
        TodoListType.WORK.value -> view.setImageResource(R.drawable.ic_work_white_24dp)
        TodoListType.STUDY.value -> view.setImageResource(R.drawable.ic_book_white_24dp)
        TodoListType.LIFE.value -> view.setImageResource(R.drawable.ic_brightness_4_white_24dp)
    }
}

@BindingAdapter("imageBGFromType")
fun bindImageBackGroundFromType(view: ImageView, type: Int) {
    when(type) {
        TodoListType.LOVE.value -> view.setImageResource(R.color.red_f44336)
        TodoListType.WORK.value -> view.setImageResource(R.color.brown_6D4C41)
        TodoListType.STUDY.value -> view.setImageResource(R.color.blue_2196F3)
        TodoListType.LIFE.value -> view.setImageResource(R.color.yellow_FFEB3B)
    }
}

@BindingAdapter("visibleFromStatus")
fun setVisibilityFromStatus(view: TextView, status: Int) {
    if (status == 1) {
        view.visibility = View.VISIBLE
    }else {
        view.visibility = View.GONE
    }
}

