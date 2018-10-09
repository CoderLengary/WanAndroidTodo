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

package com.example.lengary_l.wanandroidtodo

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.lengary_l.wanandroidtodo.component.AnimationButton
import com.example.lengary_l.wanandroidtodo.injection.Injection
import com.example.lengary_l.wanandroidtodo.ui.login.LoginActivity
import com.example.lengary_l.wanandroidtodo.util.SharedPreferencesUtils
import com.example.lengary_l.wanandroidtodo.viewmodels.LoginDataViewModel
import com.example.lengary_l.wanandroidtodo.viewmodels.TodoDataViewModel
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    private val mLoginDataViewModelFactory by lazy {
        Injection.provideLoginDataViewModelFactory()
    }

    private val mLoginDataViewModel by lazy {
        ViewModelProviders.of(this, mLoginDataViewModelFactory)
                .get(LoginDataViewModel::class.java)
    }

    private val mTodoDataViewModelFactory by lazy {
        Injection.provideTodoDataViewModelFactory(this)
    }

    private val mTodoDataViewModel by lazy {
        ViewModelProviders.of(this, mTodoDataViewModelFactory)
                .get(TodoDataViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        waveView.startPlay()
        animationButton.setAnimationButtonListener(object : AnimationButton.AnimationButtonListener{
            override fun animationFinish() {
                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

        })

        subscribeUi()
        initData()


    }


    override fun onPause() {
        super.onPause()
        stop()
    }

    private fun stop() {
        waveView.stopPlay()
        animationButton.stop()
    }

    private fun initData() {
        val id = intent.getIntExtra(SharedPreferencesUtils.USER_ID, -1)
        val userName = intent.getStringExtra(SharedPreferencesUtils.USER_NAME)
        val userPassword = intent.getStringExtra(SharedPreferencesUtils.USER_PASSWORD)
        if (SharedPreferencesUtils.getAutoLogin()) {
            mLoginDataViewModel.autoLogin(userName, userPassword)
        }else {
            SharedPreferencesUtils.putUserId(id)
            SharedPreferencesUtils.putUserName(userName)
            SharedPreferencesUtils.putUserPassword(userPassword)
            SharedPreferencesUtils.putAutoLogin(true)
            loadData()
        }

    }

    //We need to get all the data, and save them to database.
    private fun loadData() {
        //In order to save the correct data, we clear the database first.
        mTodoDataViewModel.clearAllTodo()
        mTodoDataViewModel.getAllRemoteTodoData()
    }

    private fun subscribeUi() {
        mLoginDataViewModel.autoLoginData.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                if (it == LoginDataViewModel.AUTO_LOGIN_SUCCESS) {
                    loadData()
                }else {
                    showAutoLoginErrorAlertDialog()
                }
            }
        })

        mTodoDataViewModel.getAllRemoteTodoError.observe(this, Observer {
            it?.let {
                showLoadDataErrorAlertDialog()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backToLogin()
    }

    private fun backToLogin() {
        animationButton.stop()
        SharedPreferencesUtils.putAutoLogin(false)
        SharedPreferencesUtils.putUserId(-1)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun showAutoLoginErrorAlertDialog() {
        stop()
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(R.string.home_error_alert_dialog_title)
        alertDialog.setCancelable(false)
        alertDialog.setOnKeyListener { _, _, _ -> false }
        alertDialog.setMessage(getString(R.string.home_error_alert_dialog_auto_login_content))

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.home_error_alert_dialog_back)) { _, _ ->
            backToLogin()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun showLoadDataErrorAlertDialog() {
        stop()
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(R.string.home_error_alert_dialog_title)
        alertDialog.setCancelable(false)
        alertDialog.setOnKeyListener { _, _, _ -> false }
        alertDialog.setMessage(getString(R.string.home_error_alert_dialog_load_data_content))

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.home_error_alert_dialog_close)) { _, _ ->
            finish()
            alertDialog.dismiss()
        }
        alertDialog.show()

    }

}
