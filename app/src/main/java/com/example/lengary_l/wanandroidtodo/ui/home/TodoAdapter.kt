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
import com.example.lengary_l.wanandroidtodo.data.TodoListData
import com.example.lengary_l.wanandroidtodo.databinding.ItemRecyclerviewDateBinding
import com.example.lengary_l.wanandroidtodo.databinding.ItemRecyclerviewNormalBinding
import com.example.lengary_l.wanandroidtodo.interfaze.OnRecyclerViewItemOnClickListener
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by CoderLengary
 */
class TodoAdapter( private val allList: MutableList<TodoListData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListener: OnRecyclerViewItemOnClickListener ?= null
    private val mWrapperList: MutableList<ItemWrapper> = mutableListOf()
    private val dateList: MutableList<Long> = mutableListOf()
    private val contentList: MutableList<TodoDetailData> = mutableListOf()
    private var index = 0
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    init {

        for (i in allList.indices) {
            val iwDate = ItemWrapper(ItemWrapper.TYPE_DATE)
            iwDate.index = i
            mWrapperList.add(iwDate)
            val todoListData = allList[i]
            dateList.add(todoListData.date)

            for (todoDetailData in todoListData.list) {
                val iwNormal = ItemWrapper(ItemWrapper.TYPE_NORMAL)
                iwNormal.index = index++
                mWrapperList.add(iwNormal)
                contentList.add(todoDetailData)
            }
        }



    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        ItemWrapper.TYPE_DATE ->
            ItemDateViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_recyclerview_date, viewGroup, false))
        ItemWrapper.TYPE_NORMAL ->
            ItemNormalViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_recyclerview_normal, viewGroup, false))
        else -> ItemNormalViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_recyclerview_normal, viewGroup, false))
    }

    override fun getItemCount(): Int = mWrapperList.size

    override fun getItemViewType(position: Int): Int =mWrapperList[position].viewType

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {

        val viewType = mWrapperList[i]
        when(viewType.viewType) {
            ItemWrapper.TYPE_DATE -> {
                val date = dateList[viewType.index]
                with((viewHolder as ItemDateViewHolder)) {
                    val date = Date(date)
                    val result = simpleDateFormat.format(date)
                    binding.date="Except date of complication: $result"
                }
            }

            ItemWrapper.TYPE_NORMAL -> {
                val todoDetailData = contentList[viewType.index]
                with((viewHolder as ItemNormalViewHolder)) {
                    binding.todo = todoDetailData
                    binding.clickListener = View.OnClickListener {
                        mListener?.onItemClick(it, layoutPosition)
                    }
                }
            }
        }

    }

    fun updateData(list: List<TodoListData>) {
        allList.clear()
        dateList.clear()
        contentList.clear()
        mWrapperList.clear()
        index = 0
        allList.addAll(list)
        for (i in allList.indices) {
            val iwDate = ItemWrapper(ItemWrapper.TYPE_DATE)
            iwDate.index = i
            mWrapperList.add(iwDate)
            val todoListData = allList[i]
            dateList.add(todoListData.date)

            for (todoDetailData in todoListData.list) {
                val iwNormal = ItemWrapper(ItemWrapper.TYPE_NORMAL)
                iwNormal.index = index++
                mWrapperList.add(iwNormal)
                contentList.add(todoDetailData)
            }
        }
        notifyDataSetChanged()

    }

    fun setItemClickListener(listener: OnRecyclerViewItemOnClickListener) {
        mListener = listener
    }

    fun getContentByPosition(position: Int): TodoDetailData? {
        val iw = mWrapperList[position]
        return if (iw.viewType == ItemWrapper.TYPE_NORMAL){
            contentList[iw.index]
        }else {
            null
        }
    }


    class ItemNormalViewHolder(val binding: ItemRecyclerviewNormalBinding): RecyclerView.ViewHolder(binding.root)


    class ItemDateViewHolder(val binding: ItemRecyclerviewDateBinding): RecyclerView.ViewHolder(binding.root)


    class ItemWrapper(val viewType: Int) {

        var index: Int = 0
        companion object {
            const val TYPE_DATE = 0x07
            const val TYPE_NORMAL = 0x08

        }
    }
}