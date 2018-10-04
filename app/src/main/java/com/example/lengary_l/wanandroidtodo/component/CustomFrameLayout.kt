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

package com.example.lengary_l.wanandroidtodo.component

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.haibin.calendarview.CalendarLayout

/**
 * Created by CoderLengary
 */

class CustomFrameLayout: FrameLayout, CalendarLayout.CalendarScrollView{

    private val mRecyclerView: RecyclerView by lazy {
        getChildAt(0) as RecyclerView
    }

    override fun isScrollToTop(): Boolean {
        return mRecyclerView != null && mRecyclerView.computeVerticalScrollOffset() === 0
    }



    constructor(context: Context):super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

}