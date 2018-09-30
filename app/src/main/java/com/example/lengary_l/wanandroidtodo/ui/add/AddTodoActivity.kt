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

package com.example.lengary_l.wanandroidtodo.ui.add

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.data.TodoListType
import com.example.lengary_l.wanandroidtodo.injection.Injection
import com.example.lengary_l.wanandroidtodo.util.isNotValid
import com.example.lengary_l.wanandroidtodo.viewmodels.TodoDataViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by CoderLengary
 */
class AddTodoActivity: AppCompatActivity() {

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private lateinit var todoListType: TodoListType
    private lateinit var selectTime: String

    private val mFactory by lazy {
        Injection.provideTodoDataViewModelFactory(this)
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this, mFactory)
                .get(TodoDataViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        initViews()
        setSupportActionBar(toolBar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }
        initTimePicker()
        subscribeUi()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            android.R.id.home -> onBackPressed()
            R.id.menu_add -> submitTodo()
        }
        return true
    }


    private fun submitTodo() {
        val title = editTitle.editableText.toString()
        if (title.isNotValid()) {
            Toast.makeText(this, getString(R.string.error_title), Toast.LENGTH_SHORT).show()
            return
        }
        val desc = editDesc.editableText.toString()
        Log.e("AddTodoActivity", title+desc+selectTime+todoListType.value)
        mViewModel.submitTodo(title, desc, selectTime, todoListType!!)

    }

    private fun subscribeUi() {

        mViewModel.addStatusData.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                if (it == TodoDataViewModel.SUCCESS_MSG) {
                    onBackPressed()
                }

            }
        })




    }

    override fun onBackPressed() {
        mViewModel.clearStatusData()
        super.onBackPressed()
    }

    private fun initViews(){
        radioGroup.setOnCheckedChangeListener{_, checkId ->
            todoListType = when(checkId) {
                R.id.radioLove -> TodoListType.LOVE
                R.id.radioWork -> TodoListType.WORK
                R.id.radioStudy -> TodoListType.STUDY
                R.id.radioLife -> TodoListType.LIFE
                else -> TodoListType.LOVE
            }
        }

        radioGroup.check(R.id.radioLove)
    }

    private fun initTimePicker() {

        //Get the current date
        val minDate = Calendar.getInstance()
        minDate.timeZone = TimeZone.getTimeZone("GMT+08")
        mYear = minDate.get(Calendar.YEAR)
        mMonth = minDate.get(Calendar.MONTH)
        mDay = minDate.get(Calendar.DAY_OF_MONTH)

        //Show current date by default
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val result = simpleDateFormat.format(minDate.time)
        selectTime = result

        setTextTime(selectTime)

        textTimePicker.setOnClickListener {
            val c = Calendar.getInstance()
            c.set(mYear, mMonth, mDay)
            val dialog = DatePickerDialog.newInstance({_, year, monthOfYear, dayOfMonth ->
                mYear = year
                mMonth = monthOfYear
                mDay = dayOfMonth
                c.set(mYear, mMonth, mDay)
                val chooseDate = simpleDateFormat.format(c.time)
                selectTime = chooseDate
                setTextTime(selectTime)
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

            dialog.minDate = minDate
            dialog.vibrate(false)
            dialog.show(fragmentManager, AddTodoActivity::class.java.simpleName)
        }
    }

    private fun setTextTime(selectTime: String) {
        var time = "${getString(R.string.todo_time)}\n$selectTime "
        val spannable = SpannableStringBuilder(time)
        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, time.length - selectTime.length -1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(StyleSpan(Typeface.NORMAL), time.length - selectTime.length, time.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textTimePicker.text = spannable
    }


}