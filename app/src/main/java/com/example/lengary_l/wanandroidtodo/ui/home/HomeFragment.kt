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

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoDetailData
import com.example.lengary_l.wanandroidtodo.data.TodoListType
import com.example.lengary_l.wanandroidtodo.databinding.AlertDialogLayoutBinding
import com.example.lengary_l.wanandroidtodo.databinding.FragmentHomeBinding
import com.example.lengary_l.wanandroidtodo.injection.Injection
import com.example.lengary_l.wanandroidtodo.interfaze.OnRecyclerViewItemOnClickListener
import com.example.lengary_l.wanandroidtodo.util.changeToListType
import com.example.lengary_l.wanandroidtodo.viewmodels.TodoDataViewModel
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.leinardi.android.speeddial.SpeedDialActionItem
import kotlinx.android.synthetic.main.alert_dialog_layout.view.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by CoderLengary
 */
class HomeFragment: Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }


    private lateinit var mYear: String
    private lateinit var mMonth: String
    private lateinit var mDay: String

    private lateinit var mDataBinding: FragmentHomeBinding

    private var mSelectedDate = "0000-00-00"

    private var mDialog: AlertDialog? = null

    var adapter: TodoAdapter ?= null


    private val mFactory by lazy {
        Injection.provideTodoDataViewModelFactory(context!!)
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this, mFactory)
                .get(TodoDataViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        return mDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)

        //calendar
        initCalendar()

        //float action button
        initSpeedDial()

        subscribeUi()
    }




    private fun initCalendar() {
        mDataBinding.currentDay = calendarView.curDay.toString()
        initCalendarPoints()
        calendarView.setOnCalendarSelectListener(object :CalendarView.OnCalendarSelectListener{
            override fun onCalendarOutOfRange(calendar: Calendar?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                calendar?.let {
                    mYear = calendar.year.toString()
                    mMonth = calendar.month.toString()
                    //To make the date format of calendar be compatible with the one of database
                    //sample: calendar 2018 9 1 database 2018 09 01
                    if (calendar.month < 10) {
                        mMonth = "0$mMonth"
                    }
                    mDay = calendar.day.toString()
                    if (calendar.day < 10) {
                        mDay = "0$mDay"
                    }
                    mSelectedDate = "$mYear-$mMonth-$mDay"
                    //mViewModel.changeTodoDataByDate(mSelectedDate)
                    mViewModel.getLocalTodoDataByDate(mSelectedDate)
                    adapter?.let {
                        it.closeOtherOpenItem()
                    }
                    mDataBinding.monthDay = "$mMonth / $mDay"
                    mDataBinding.year = mYear
                }
            }
        })

        currentCalenderLayout.setOnClickListener{
            calendarView.scrollToCurrent()
        }
    }

    private fun initCalendarPoints() {
        val pointDateMap= mViewModel.pointDateMap
        val dateStrList = pointDateMap.keys.filter {
            pointDateMap[it]!! > 0
        }
        val map = HashMap<String, Calendar>()
        for (dateStr in dateStrList) {
            val list = dateStr.split("-")
            map.put(getSchemeCalendar(list[0].toInt(), list[1].toInt(), list[2].toInt(), -0x1000000).toString(),
                   getSchemeCalendar(list[0].toInt(), list[1].toInt(), list[2].toInt(), -0x1000000))
        }
        calendarView.setSchemeDate(map)
    }

    private fun getSchemeCalendar(year: Int, month: Int, day: Int, color: Int): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color
        return calendar
    }

    private fun initSpeedDial() {
        speedDial.addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_love, R.mipmap.ic_love_100)
                        .setFabBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                        .setLabel(R.string.type_love)
                         .create())
        speedDial.addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_life, R.mipmap.ic_life_100)
                        .setFabBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                        .setLabel(R.string.type_life)
                        .create())
        speedDial.addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_study, R.mipmap.ic_books_100)
                        .setFabBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                        .setLabel(R.string.type_study)
                        .create())
        speedDial.addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_work, R.mipmap.ic_work_100)
                        .setFabBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                        .setLabel(R.string.type_work)
                        .create())

        speedDial.setOnActionSelectedListener {
            when(it.id) {
                R.id.fab_love -> openAlertDialog(null,  TodoListType.LOVE)
                R.id.fab_life -> openAlertDialog(null,  TodoListType.LIFE)
                R.id.fab_study -> openAlertDialog(null,  TodoListType.STUDY)
                R.id.fab_work -> openAlertDialog(null,  TodoListType.WORK)
            }
            return@setOnActionSelectedListener true
        }
    }


    private fun openAlertDialog(data: TodoDetailData?, type: TodoListType) {
        val builder = AlertDialog.Builder(context)
        mDialog = builder.create()
        mDialog?.let {
            it.show()
            //customize the dialog
            val window = it.window
            val view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_layout, null, false)
            val mAlertDialogBinding = AlertDialogLayoutBinding.bind(view)
            window.setContentView(view)
            val params = window.attributes
            params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            window.attributes = params
            mAlertDialogBinding.todoType = type.value
            mAlertDialogBinding.selectedDate = mSelectedDate
            with(view) {

                data?.let {
                    editTitle.setText(it.title)
                    editTitle.setSelection(it.title.length)
                    editContent.setText(it.content)
                    btnSubmit.setText(R.string.home_custom_alert_dialog_btn_update)
                }

                btnSubmit.setOnClickListener {
                    val editTitle = editTitle.editableText
                    val editContent = editContent.editableText
                    when {
                        editTitle.isEmpty() -> Toast.makeText(context, "Title is non null", Toast.LENGTH_SHORT).show()
                        data != null -> mViewModel.updateTodo(data.id, editTitle.toString(), editContent.toString(), data.dateStr, data.status, data.type)
                        else -> mViewModel.submitTodo(editTitle.toString(), editContent.toString(), mSelectedDate, type)
                    }
                }
            }
        }
    }


    private fun subscribeUi() {

        mViewModel.todoList.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                recyclerView.visibility = View.VISIBLE
                emptyLayout.visibility = View.INVISIBLE
                if (adapter == null) {
                    adapter = TodoAdapter(list as MutableList<TodoDetailData>)
                    adapter?.setItemClickListener(object : OnRecyclerViewItemOnClickListener{
                        override fun onCompelteClick(data: TodoDetailData) {
                            mViewModel.updateTodo(data.id, data.title, data.content, data.dateStr, 1, data.type)
                        }

                        override fun onRevertClick(data: TodoDetailData) {
                            mViewModel.updateTodo(data.id, data.title, data.content, data.dateStr, 0, data.type)
                        }

                        override fun onDeleteClick(data: TodoDetailData) {
                            mViewModel.deleteTodo(data.id)
                        }

                        override fun onContentClick(data: TodoDetailData) {
                            openAlertDialog(data, data.type.changeToListType())
                        }

                    })
                    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                            super.onScrollStateChanged(recyclerView, newState)
                            adapter!!.setScrollingItem(null)
                        }
                    })
                    recyclerView.adapter = adapter
                }else {
                    adapter?.update(list as MutableList<TodoDetailData>)
                }
            }
        })

        mViewModel.todoListError.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerView.visibility = View.INVISIBLE
                emptyLayout.visibility = View.VISIBLE
            }
        })




        mViewModel.getAllTodoError.observe(viewLifecycleOwner, Observer {
            showAlertDialog()
        })

        mViewModel.statusData.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    TodoDataViewModel.ADD_SUCCESS_MSG -> {
                        mDialog?.let {
                            it.dismiss()
                            mViewModel.getLocalTodoDataByDate(mSelectedDate)
                        }
                    }
                    TodoDataViewModel.UPDATE_SUCCESS_MSG -> {
                        mDialog?.let {
                            it.dismiss()
                            mViewModel.getLocalTodoDataByDate(mSelectedDate)
                        }
                    }
                    TodoDataViewModel.DELETE_SUCCESS_MSG -> {
                        mViewModel.getLocalTodoDataByDate(mSelectedDate)
                    }
                    else -> Toast.makeText(context, it, Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    private fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(R.string.home_alert_dialog_title)
        alertDialog.setMessage(getString(R.string.home_alert_dialog_content))
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.home_alert_dialog_positive)) { _, _ ->
            refresh()
            alertDialog.dismiss()
        }
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.home_alert_dialog_negative)) { _, _ ->
            alertDialog.dismiss()
            closeApp()
        }
        alertDialog.show()

    }

    private fun closeApp() {
        activity?.finish()
    }

    private fun refresh() {
        mViewModel.getAllRemoteTodoData()
    }

}