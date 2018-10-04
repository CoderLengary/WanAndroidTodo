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
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.example.lengary_l.wanandroidtodo.ui.home.TodoAdapter

/**
 * Created by CoderLengary
 */

//当前item滑动->检查是否有正在滑动的item->有的话，阻止当前item的滑动
//没有的话，关闭掉已经滑动好的item，在Adapter中标记当前item为滑动item，在action_up中执行滑动处理逻辑，注意要返回true/false，否则不会起效
//成功滑动的话，标记当前item为滑动好的item，并清除adapter中正在滑动的item(毕竟已经滑动完成了)
//监听的情况，不能直接设置监听，否则无法响应ActionDown
class CustomHorizontalScrollView: HorizontalScrollView {

    private var mScreenWidth: Int

    private var mMenuWidth: Int

    private var isOpen = false

    private var once = true

    private var downTime: Long = 0

    var downX:Float = 0.0.toFloat()

    var canClick = true

    companion object {
        private const val SUCCESS_DURATION = 100
        private const val RATIO = 0.2f
    }

    constructor(context: Context, attrs: AttributeSet):super(context, attrs) {

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE)
        val outMetrics = DisplayMetrics()
        (windowManager as WindowManager).defaultDisplay.getMetrics(outMetrics)
        mScreenWidth =  outMetrics.widthPixels
        mMenuWidth = (mScreenWidth*RATIO).toInt()
        overScrollMode = View.OVER_SCROLL_NEVER
        isHorizontalScrollBarEnabled = false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        if (once) {
            val linearLayout = getChildAt(0) as LinearLayout
            linearLayout.getChildAt(0).layoutParams.width = mScreenWidth - (2*paddingLeft)

            linearLayout.getChildAt(1).layoutParams.width = mMenuWidth
            linearLayout.getChildAt(2).layoutParams.width = mMenuWidth
            once =false
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    private val mAdaper: TodoAdapter by lazy {
        var view: View = this
        //conduct the function until we find the recycler view
        while (true) {
            view = view.parent as View
            if (view is RecyclerView) {
                break
            }
        }
        (view as RecyclerView).adapter as TodoAdapter
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {

        //If another view is scrolling, we should not handle any motion event of this one
        if (mAdaper.getScrollingItem() != null && mAdaper.getScrollingItem() != this) {
            return false
        }
        var x = ev?.x

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                downTime = System.currentTimeMillis()
                if (!isOpen) {
                    canClick = !mAdaper.closeOtherOpenItem()
                }
                mAdaper.setScrollingItem(this)
                downX = x!!

            }

            MotionEvent.ACTION_UP -> {

                mAdaper.setScrollingItem(null)
                if (System.currentTimeMillis() - downTime < SUCCESS_DURATION && scrollX == 0) {
                    if (canClick) {
                        mOnCustomClickListener?.onClick()
                    }
                    return false
                }

                val deltaX = x!! - downX

                if (deltaX < 0 && Math.abs(deltaX) > mMenuWidth) {
                    smoothScrollTo(2 * mMenuWidth + paddingRight, 0)
                    mAdaper.setOpenItem(this)
                    isOpen = true
                }else if (deltaX < 0 && Math.abs(deltaX) < mMenuWidth ) {
                    smoothScrollTo(0, 0)
                    mAdaper.setOpenItem(null)
                }else if (deltaX > 0 && Math.abs(deltaX) < mMenuWidth && isOpen){
                    smoothScrollTo(2 * mMenuWidth + paddingRight, 0)
                    mAdaper.setOpenItem(this)
                    isOpen = true
                }else {
                    smoothScrollTo(0, 0)
                    mAdaper.setOpenItem(null)
                    isOpen = false
                }


                return false
            }

        }


        return super.onTouchEvent(ev)
    }

    fun close() {
        this.smoothScrollTo(0,0)
        isOpen = false
    }

    fun isOpen() = isOpen


    interface OnCustomClickListener{
        fun onClick()
    }
    private var mOnCustomClickListener: OnCustomClickListener?=null

    fun setOnCustomClickListener(onCustomClickListener: OnCustomClickListener) {
        mOnCustomClickListener = onCustomClickListener

    }
}