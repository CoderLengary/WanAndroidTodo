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

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData
import com.example.lengary_l.wanandroidtodo.databinding.ItemRecyclerviewNormalBinding
import com.example.lengary_l.wanandroidtodo.interfaze.OnRecyclerViewItemOnClickListener

/**
 * Created by CoderLengary
 */
class TodoAdapter(private val mList: MutableList<TodoDetailData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListener: OnRecyclerViewItemOnClickListener ?= null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemNormalViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_recyclerview_normal, viewGroup, false))
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val todoDetailData = mList[i]
        with(viewHolder as ItemNormalViewHolder) {
            binding.todo = todoDetailData
            binding.clickListener = View.OnClickListener {
                mListener?.onItemClick(it, layoutPosition)
            }
        }
    }

    fun updateData(list: List<TodoDetailData>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
        notifyItemRemoved(list.size)
    }

    fun setItemClickListener(listener: OnRecyclerViewItemOnClickListener) {
        mListener = listener
    }
    class ItemNormalViewHolder(val binding: ItemRecyclerviewNormalBinding): RecyclerView.ViewHolder(binding.root)

}