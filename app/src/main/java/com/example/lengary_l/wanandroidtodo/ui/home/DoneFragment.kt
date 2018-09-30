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
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData
import com.example.lengary_l.wanandroidtodo.injection.Injection
import com.example.lengary_l.wanandroidtodo.interfaze.OnRecyclerViewItemOnClickListener
import com.example.lengary_l.wanandroidtodo.viewmodels.TodoDataViewModel
import kotlinx.android.synthetic.main.fragment_home_page.*

/**
 * Created by CoderLengary
 */
class DoneFragment: Fragment() {

    companion object {
        fun newIntance() = DoneFragment()
    }

    private val mFactory by lazy {
        Injection.provideTodoDataViewModelFactory(context!!)
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this, mFactory)
                .get(TodoDataViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_home_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        subscribeUi(recyclerView)
    }

    private fun subscribeUi(recyclerView: RecyclerView) {
        var adapter: TodoAdapter?= null

        mViewModel.doneTodoList.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                recyclerView.visibility = View.VISIBLE
                empty_layout.visibility = View.GONE
                if (adapter == null) {
                    adapter = TodoAdapter(list as MutableList<TodoDetailData>)
                    adapter?.setItemClickListener(object : OnRecyclerViewItemOnClickListener {
                        override fun onItemClick(v: View, position: Int) {

                        }
                    })
                    recyclerView.adapter = adapter
                }else {
                    adapter?.updateData(list)
                }
            }

        })

        mViewModel.doneTodoListError.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerView.visibility = View.INVISIBLE
                empty_layout.visibility = View.VISIBLE
            }
        })
    }

}