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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoListType
import com.example.lengary_l.wanandroidtodo.databinding.AlertDialogLayoutBinding
import com.example.lengary_l.wanandroidtodo.databinding.FragmentHomeBinding
import com.example.lengary_l.wanandroidtodo.injection.Injection
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

    private lateinit var mTodoFragment: TodoFragment
    private lateinit var mDoneFragment: DoneFragment

    private lateinit var mYear: String
    private lateinit var mMonth: String
    private lateinit var mDay: String

    private lateinit var mDataBinding: FragmentHomeBinding

    private var mCurrentListType: TodoListType ?= null

    private var mSelectedDate = "0000-00-00"

    private var mDialog: AlertDialog? = null



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

        //calendar
        initCalendar()

        //child fragment
        initFragments(savedInstanceState)
        viewPager.adapter = HomePageAdapter(
                childFragmentManager,
                context!!,
                mTodoFragment,
                mDoneFragment
        )
        viewPager.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(viewPager)


        //float action button
        initSpeedDial()

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
                    mViewModel.changeTodoDataByDate(mSelectedDate)
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
            map.put(getSchemeCalendar(list[0].toInt(), list[1].toInt(), list[2].toInt(), -0x20ecaa).toString(),
                   getSchemeCalendar(list[0].toInt(), list[1].toInt(), list[2].toInt(), -0x20ecaa))
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
                        .create())
        speedDial.addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_life, R.mipmap.ic_life_100)
                        .setFabBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                        .create())
        speedDial.addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_study, R.mipmap.ic_books_100)
                        .setFabBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                        .create())
        speedDial.addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_work, R.mipmap.ic_work_100)
                        .setFabBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                        .create())

        speedDial.setOnActionSelectedListener {
            when(it.id) {
                R.id.fab_love -> openAlertDialog(TodoListType.LOVE)
                R.id.fab_life -> openAlertDialog(TodoListType.LIFE)
                R.id.fab_study -> openAlertDialog(TodoListType.STUDY)
                R.id.fab_work -> openAlertDialog(TodoListType.WORK)
            }
            return@setOnActionSelectedListener true
        }
    }


    private fun openAlertDialog(type: TodoListType) {
        val builder= AlertDialog.Builder(context)
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
                this.btnSubmit.setOnClickListener {
                    val title = this.editTitle.editableText
                    val content = this.editContent.editableText
                    if (title.isEmpty()) {
                        Toast.makeText(context, "Title is non null", Toast.LENGTH_SHORT).show()
                    }else {
                        mViewModel.submitTodo(title.toString(), content.toString(), mSelectedDate, type )
                    }
                }
            }
        }


    }


    private fun subscribeUi() {

        mViewModel.getAllTodoError.observe(viewLifecycleOwner, Observer {
            showAlertDialog()
        })

        mViewModel.addStatusData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it == TodoDataViewModel.SUCCESS_MSG) {
                    mDialog?.let {
                        it.dismiss()
                        mViewModel.changeTodoDataByDate(mSelectedDate)
                    }
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
        mViewModel.getAllByRemote()
    }

}