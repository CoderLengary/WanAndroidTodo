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
import com.example.lengary_l.wanandroidtodo.component.CustomHorizontalScrollView
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData
import com.example.lengary_l.wanandroidtodo.databinding.ItemRecyclerViewContentBinding
import com.example.lengary_l.wanandroidtodo.interfaze.OnRecyclerViewItemOnClickListener
import kotlinx.android.synthetic.main.item_recycler_view_content.view.*
import kotlinx.android.synthetic.main.item_recyclerview_status.view.*

/**
 * Created by CoderLengary
 */
class TodoAdapter(private val mList: MutableList<TodoDetailData>): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val mWrapperList: MutableList<ItemWrapper> = mutableListOf()
    private val mIncompleteList: MutableList<TodoDetailData> = mutableListOf()
    private val mCompleteList :MutableList<TodoDetailData> = mutableListOf()

    private var mListener: OnRecyclerViewItemOnClickListener ?= null

    private var mScrollingItem: CustomHorizontalScrollView? =null
    private var mOpenItem: CustomHorizontalScrollView? =null


    fun setOpenItem(openItem: CustomHorizontalScrollView?) {
        mOpenItem = openItem
    }

    fun closeOtherOpenItem(): Boolean {
        if (mOpenItem != null && mOpenItem!!.isOpen()) {
            mOpenItem!!.close()
            mOpenItem = null
            return true
        }
        return false
    }

    fun setScrollingItem(scrollingItem: CustomHorizontalScrollView?) {
        mScrollingItem = scrollingItem
    }

    fun getScrollingItem(): CustomHorizontalScrollView? {
        return if (mScrollingItem != null) mScrollingItem else null
    }

    init {
        mIncompleteList.addAll(mList.filter { it.status==0 })
        mCompleteList.addAll(mList.filter { it.status == 1 })
        mWrapperList.add(ItemWrapper(ItemWrapper.TYPE_INCOMPLETE_STATUS))
        if (mIncompleteList.isEmpty()) {
            mWrapperList.add(ItemWrapper(ItemWrapper.TYPE_EMPTY))
        }else {
            for (i in mIncompleteList.indices) {
                val itemWrapper = ItemWrapper(ItemWrapper.TYPE_INCOMPLETE_LIST)
                itemWrapper.index = i
                mWrapperList.add(itemWrapper)
            }
        }
        mWrapperList.add(ItemWrapper(ItemWrapper.TYPE_COMPLETE_STATUS))
        if (mCompleteList.isEmpty()) {
            mWrapperList.add(ItemWrapper(ItemWrapper.TYPE_EMPTY))
        }else {
            for (i in mCompleteList.indices) {
                val itemWrapper = ItemWrapper(ItemWrapper.TYPE_COMPLETE_LIST)
                itemWrapper.index = i
                mWrapperList.add(itemWrapper)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType) {
            ItemWrapper.TYPE_INCOMPLETE_STATUS -> StatusViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recyclerview_status, viewGroup, false))
            ItemWrapper.TYPE_INCOMPLETE_LIST -> ItemContentViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_recycler_view_content, viewGroup, false))
            ItemWrapper.TYPE_COMPLETE_STATUS -> StatusViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recyclerview_status, viewGroup, false))
            ItemWrapper.TYPE_COMPLETE_LIST -> ItemContentViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_recycler_view_content, viewGroup, false))
            ItemWrapper.TYPE_EMPTY -> EmptyViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recyclerview_empty, viewGroup, false))
            else -> StatusViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recyclerview_status, viewGroup, false))
        }


    override fun getItemCount(): Int = mWrapperList.size

    override fun getItemViewType(position: Int): Int = mWrapperList[position].viewType

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val iw = mWrapperList[i]
        when (iw.viewType) {
            ItemWrapper.TYPE_INCOMPLETE_STATUS -> {
                with(viewHolder as StatusViewHolder) {
                    itemView.textStatus.text = itemView.context.getString(R.string.home_item_recycler_view_status_incomplete)
                }
            }
            ItemWrapper.TYPE_COMPLETE_STATUS -> {
                with(viewHolder as StatusViewHolder) {
                    itemView.textStatus.text = itemView.context.getString(R.string.home_item_recycler_view_status_complete)
                }
            }
            ItemWrapper.TYPE_INCOMPLETE_LIST -> {
                val incompleteTodo = mIncompleteList[iw.index]
                with(viewHolder as ItemContentViewHolder) {
                    binding.todo = incompleteTodo
                    itemView.customHorizontalView.setOnCustomClickListener(object : CustomHorizontalScrollView.OnCustomClickListener{
                        override fun onClick() {
                            if (mOpenItem == null){
                                mListener?.onContentClick(incompleteTodo)
                            }

                        }
                    })

                    binding.updateStatusListener = View.OnClickListener {
                        closeOtherOpenItem()
                        mListener?.onCompelteClick(incompleteTodo)
                    }

                    binding.deleteListener = View.OnClickListener {
                        closeOtherOpenItem()
                        mListener?.onDeleteClick(incompleteTodo)
                    }
                }
            }
            ItemWrapper.TYPE_COMPLETE_LIST -> {
                val completeTodo = mCompleteList[iw.index]
                with(viewHolder as ItemContentViewHolder) {
                    binding.todo = completeTodo
                    itemView.customHorizontalView.setOnCustomClickListener(object : CustomHorizontalScrollView.OnCustomClickListener{
                        override fun onClick() {
                            if (mOpenItem == null) {
                                mListener?.onContentClick(completeTodo)
                            }
                        }
                    })

                    binding.updateStatusListener = View.OnClickListener {
                        closeOtherOpenItem()
                        mListener?.onRevertClick(completeTodo)
                    }

                    binding.deleteListener = View.OnClickListener {
                        closeOtherOpenItem()
                        mListener?.onDeleteClick(completeTodo)
                    }
                }
            }
        }

    }


    fun update(list: MutableList<TodoDetailData>) {
        mList.clear()
        mList.addAll(list)
        mIncompleteList.clear()
        mCompleteList.clear()
        mWrapperList.clear()
        mIncompleteList.addAll(mList.filter { it.status==0 })
        mCompleteList.addAll(mList.filter { it.status == 1 })
        mWrapperList.add(ItemWrapper(ItemWrapper.TYPE_INCOMPLETE_STATUS))
        if (mIncompleteList.isEmpty()) {
            mWrapperList.add(ItemWrapper(ItemWrapper.TYPE_EMPTY))
        }else {
            for (i in mIncompleteList.indices) {
                val itemWrapper = ItemWrapper(ItemWrapper.TYPE_INCOMPLETE_LIST)
                itemWrapper.index = i
                mWrapperList.add(itemWrapper)
            }
        }
        mWrapperList.add(ItemWrapper(ItemWrapper.TYPE_COMPLETE_STATUS))
        if (mCompleteList.isEmpty()) {
            mWrapperList.add(ItemWrapper(ItemWrapper.TYPE_EMPTY))
        }else {
            for (i in mCompleteList.indices) {
                val itemWrapper = ItemWrapper(ItemWrapper.TYPE_COMPLETE_LIST)
                itemWrapper.index = i
                mWrapperList.add(itemWrapper)
            }
        }
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: OnRecyclerViewItemOnClickListener) {
        mListener = listener
    }


    class ItemContentViewHolder(val binding: ItemRecyclerViewContentBinding): RecyclerView.ViewHolder(binding.root)

    class StatusViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    class ItemWrapper(val viewType: Int) {
        var index: Int = 0

        companion object {

            const val TYPE_INCOMPLETE_STATUS = 0x00
            const val TYPE_INCOMPLETE_LIST = 0x01
            const val TYPE_EMPTY = 0x02
            const val TYPE_COMPLETE_STATUS = 0x03
            const val TYPE_COMPLETE_LIST = 0x04
        }
    }

}