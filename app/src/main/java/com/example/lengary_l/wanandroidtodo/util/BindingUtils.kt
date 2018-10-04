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
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoListType

/**
 * Created by CoderLengary
 */

@BindingAdapter("imageFromType")
fun bindImageFromType(view: ImageView, type: Int) {
    when(type) {
        TodoListType.LOVE.value -> view.setImageResource(R.mipmap.ic_love_100)
        TodoListType.WORK.value -> view.setImageResource(R.mipmap.ic_work_100)
        TodoListType.STUDY.value -> view.setImageResource(R.mipmap.ic_books_100)
        TodoListType.LIFE.value -> view.setImageResource(R.mipmap.ic_life_100)
    }
}

@BindingAdapter("layoutBGFromType")
fun bindLayoutBackGroundFromType(view: LinearLayout, type: Int) {

    when(type) {
        TodoListType.LOVE.value -> view.setBackgroundResource(R.color.pink_ed7899)
        TodoListType.WORK.value -> view.setBackgroundResource(R.color.blue_cbded6)
        TodoListType.STUDY.value -> view.setBackgroundResource(R.color.yellow_fcca3d)
        TodoListType.LIFE.value -> view.setBackgroundResource(R.color.orange_ff5722)
    }
}

@BindingAdapter("btnTextColorFromType")
fun bindButtonTextColorFromType(view: Button, type: Int) {
    when(type) {
        TodoListType.LOVE.value -> view.setTextColor(view.context.getColor(R.color.pink_ed7899))
        TodoListType.WORK.value -> view.setTextColor(view.context.getColor(R.color.blue_cbded6))
        TodoListType.STUDY.value -> view.setTextColor(view.context.getColor(R.color.yellow_fcca3d))
        TodoListType.LIFE.value -> view.setTextColor(view.context.getColor(R.color.orange_ff5722))
    }
}

@BindingAdapter("textFromStatus")
fun bindTextViewTextFromStatus(view: TextView, status: Int) {
    if (status == 0) {
        view.text = view.context.getText(R.string.home_item_recycler_view_complete)
    }else if (status == 1) {
        view.text = view.context.getText(R.string.home_item_recycler_view_revert)
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

