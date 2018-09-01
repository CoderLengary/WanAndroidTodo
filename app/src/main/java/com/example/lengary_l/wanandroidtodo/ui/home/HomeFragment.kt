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

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoListType
import com.example.lengary_l.wanandroidtodo.injection.Injection
import com.example.lengary_l.wanandroidtodo.ui.add.AddTodoActivity
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

    private var mCurrentListType: TodoListType ?= null

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

        //Default : love
        mCurrentListType = changeType(TodoListType.LOVE)
        fab.setOnClickListener {
            val intent = Intent(context, AddTodoActivity::class.java)
            startActivity(intent)
        }
        subscribeUi()
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
        inflater?.inflate(R.menu.menu_home, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId
        mCurrentListType = when(id) {
            R.id.menu_love -> TodoListType.LOVE
            R.id.menu_life -> TodoListType.LIFE
            R.id.menu_study -> TodoListType.STUDY
            R.id.menu_work -> TodoListType.WORK
            else -> null
        }
        mCurrentListType?.let { changeType(it) }
        return super.onOptionsItemSelected(item)

    }

    private fun changeType(type: TodoListType): TodoListType {
        mViewModel.changeTodoDataByType(type)
        return type
    }

    private fun subscribeUi() {

        //if updateType equals current listType. We should update the list manually.
        //Otherwise the list will never show the latest data.
        mViewModel.updateType.observe(viewLifecycleOwner, Observer {
            when(it) {
                mCurrentListType -> mCurrentListType?.let { changeType(it) }
                else -> Unit
            }
        })
    }

}