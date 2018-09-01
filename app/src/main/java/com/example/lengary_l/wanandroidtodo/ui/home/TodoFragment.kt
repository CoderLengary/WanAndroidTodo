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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData
import com.example.lengary_l.wanandroidtodo.injection.Injection
import com.example.lengary_l.wanandroidtodo.interfaze.OnRecyclerViewItemOnClickListener
import com.example.lengary_l.wanandroidtodo.util.changeToListType
import com.example.lengary_l.wanandroidtodo.viewmodels.TodoDataViewModel
import kotlinx.android.synthetic.main.fragment_home_page.*

/**
 * Created by CoderLengary
 */
class TodoFragment : Fragment() {

    companion object {
        fun newInstance() = TodoFragment()
    }

    private val mFactory by lazy {
        Injection.provideTodoDataViewModelFactory()
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
        var adapter: TodoAdapter? = null
        Log.e("Todo", mViewModel.toString())
        mViewModel.liveTodoData.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)
                if (adapter == null) {
                    adapter = TodoAdapter(list as MutableList<TodoDetailData>)
                    adapter?.setItemClickListener(object : OnRecyclerViewItemOnClickListener{
                        override fun onItemClick(v: View, position: Int) {
                            val item = list[position]
                            mViewModel.updateTodo(item.id, item.title, item.content, item.dateStr, 1, item.type.changeToListType())
                        }
                    })
                    recyclerView.adapter = adapter
                    recyclerView.layoutAnimation = controller
                }else {
                    adapter?.updateData(list)
                }
            }
        })

        mViewModel.updateStatusData.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })



    }
}