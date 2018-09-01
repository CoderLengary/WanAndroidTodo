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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData
import com.example.lengary_l.wanandroidtodo.data.TodoListType
import com.example.lengary_l.wanandroidtodo.interfaze.OnRecyclerViewItemOnClickListener
import kotlinx.android.synthetic.main.item_recyclerview.view.*

/**
 * Created by CoderLengary
 */
class TodoAdapter(private val dataList: MutableList<TodoDetailData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListener: OnRecyclerViewItemOnClickListener ?= null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder =
            ItemViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_recyclerview, p0, false), mListener)

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val todoDetailData = dataList[p1]
        with((p0 as ItemViewHolder).itemView) {
            imgType.let {
                when(todoDetailData.type) {
                    TodoListType.LOVE.value -> it.setImageResource(R.drawable.ic_favorite_white_24dp)
                    TodoListType.WORK.value -> it.setImageResource(R.drawable.ic_work_white_24dp)
                    TodoListType.STUDY.value -> it.setImageResource(R.drawable.ic_book_white_24dp)
                    TodoListType.LIFE.value -> it.setImageResource(R.drawable.ic_brightness_4_white_24dp)
                }
            }

            circleView.let {
                when(todoDetailData.type) {
                    TodoListType.LOVE.value -> it.setImageResource(R.color.red_f44336)
                    TodoListType.WORK.value -> it.setImageResource(R.color.brown_6D4C41)
                    TodoListType.STUDY.value -> it.setImageResource(R.color.blue_2196F3)
                    TodoListType.LIFE.value -> it.setImageResource(R.color.yellow_FFEB3B)
                }
            }

            textTitle.text = todoDetailData.title
            textContent.text = todoDetailData.content
        }
    }

    fun updateData(list: List<TodoDetailData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
        notifyItemRemoved(list.size)
    }

    fun setItemClickListener(listener: OnRecyclerViewItemOnClickListener) {
        mListener = listener
    }

    class ItemViewHolder(itemView: View,
                         private val mListener: OnRecyclerViewItemOnClickListener?): RecyclerView.ViewHolder(itemView), View.OnClickListener {


        init {
            with(itemView) {
                imgCheck.setOnClickListener(this@ItemViewHolder)
            }
        }

        override fun onClick(p0: View?) {
            when(p0?.id) {
                R.id.imgCheck -> mListener?.onItemClick(p0, layoutPosition)
            }
        }
    }
}