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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lengary_l.wanandroidtodo.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by CoderLengary
 */
class HomeFragment: Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var mTodoFragment: TodoFragment
    private lateinit var mDoneFragment: DoneFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragments(savedInstanceState)
        viewPager.adapter = HomePageAdapter(
                childFragmentManager,
                context!!,
                mTodoFragment,
                mDoneFragment
        )
        viewPager.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(viewPager)

    }
    private fun initFragments(savedInstanceState: Bundle?) {
        val fm = childFragmentManager
        if (savedInstanceState == null) {
            mTodoFragment = TodoFragment.newInstance()
            mDoneFragment = DoneFragment.newIntance()
        } else {
            mTodoFragment = fm.getFragment(savedInstanceState, TodoFragment::class.java.simpleName) as TodoFragment
            mDoneFragment = fm.getFragment(savedInstanceState, DoneFragment::class.java.simpleName) as DoneFragment
        }
    }

}