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

package com.example.lengary_l.wanandroidtodo.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoListType
import com.example.lengary_l.wanandroidtodo.injection.Injection
import com.example.lengary_l.wanandroidtodo.viewmodels.TodoDataViewModel
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

    private val mFactory by lazy {
        Injection.provideTodoDataViewModelFactory()
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this, mFactory)
                .get(TodoDataViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initFragments(savedInstanceState)
        viewPager.adapter = HomePageAdapter(
                childFragmentManager,
                context!!,
                mTodoFragment,
                mDoneFragment
        )
        viewPager.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(viewPager)
        changeType(TodoListType.LOVE)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        Log.e("HomeFragment", "createMenu is running")
        inflater?.inflate(R.menu.menu_select, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId
        when(id) {
            R.id.menu_love -> changeType(TodoListType.LOVE)
            R.id.menu_life -> changeType(TodoListType.LIFE)
            R.id.menu_study -> changeType(TodoListType.STUDY)
            R.id.menu_work -> changeType(TodoListType.WORK)
        }
        return super.onOptionsItemSelected(item)

    }

    private fun changeType(type: TodoListType) {
        mViewModel.changeTodoDataByType(type)
    }

}