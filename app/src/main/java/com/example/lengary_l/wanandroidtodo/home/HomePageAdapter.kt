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

package com.example.lengary_l.wanandroidtodo.home

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.lengary_l.wanandroidtodo.R

/**
 * Created by CoderLengary
 */
class HomePageAdapter(fm: FragmentManager,
                      context: Context,
                      private val mTodoFragment: TodoFragment,
                      private val mDoneFragment: DoneFragment): FragmentPagerAdapter(fm) {

    private val pageCount = 2

    private val strings: Array<String> = arrayOf(
            context.getString(R.string.home_todo),
            context.getString(R.string.home_done)
            )

    override fun getItem(position: Int): Fragment =
         when(position){
            0 -> mTodoFragment
            else -> mDoneFragment
         }




    override fun getCount(): Int {
        return pageCount
    }

    override fun getPageTitle(position: Int): CharSequence? = strings[position]
}