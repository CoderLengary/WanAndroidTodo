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

package com.example.lengary_l.wanandroidtodo.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.WelcomeActivity
import com.example.lengary_l.wanandroidtodo.data.PostType
import com.example.lengary_l.wanandroidtodo.injection.Injection
import com.example.lengary_l.wanandroidtodo.util.SharedPreferencesUtils
import com.example.lengary_l.wanandroidtodo.util.isNotValid
import com.example.lengary_l.wanandroidtodo.viewmodels.LoginDataViewModel
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by CoderLengary
 */
class LoginFragment : Fragment() {


    companion object {
        fun newInstance() = LoginFragment()
    }

    private val mFactory by lazy {
        Injection.provideLoginDataViewModelFactory(context!!)
    }

    private val mViewModel: LoginDataViewModel by lazy {
         ViewModelProviders.of(this, mFactory)
                .get(LoginDataViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SharedPreferencesUtils.getAutoLogin()) {
            navigateToWelcome(SharedPreferencesUtils.getUserId(),SharedPreferencesUtils.getUserName(), SharedPreferencesUtils.getUserPassword())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()

        with(context) {
            val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.down_in)
            linear_layout?.startAnimation(translateAnimation)
        }

        linkToRegister.setOnClickListener {
            (activity as LoginActivity).showFragment(FragmentType.REGISTER)
        }


        editUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputLayoutUser.error = null
                return
            }

        })
        editPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputLayoutPassword.error = null
                return
            }

        })

        btnLogin.setOnClickListener {
            val userName: String = editUser.editableText.toString()
            val passWord: String = editPassword.editableText.toString()

            if (userName.isNotValid()) {
                inputLayoutUser.error = getString(R.string.error_user_name)
                return@setOnClickListener
            }

            if (passWord.isNotValid()) {
                inputLayoutPassword.error = getString(R.string.error_password)
                return@setOnClickListener
            }

            mViewModel.setInput(userName, passWord, PostType.TYPE_LOGIN)
        }


    }

    private fun subscribeUi() {
        mViewModel.loginData.observe(viewLifecycleOwner, Observer {
            it?.let {
                val data = it.data
                navigateToWelcome(data.id, data.username, data.password)
            }
        })

        mViewModel.loginExceptionData.observe(viewLifecycleOwner, Observer {
            inputLayoutUser.error = it
        })


    }

    private fun navigateToWelcome(id: Int, userName: String, userPassword: String) {
        val intent = Intent(context, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(SharedPreferencesUtils.USER_ID, id)
        intent.putExtra(SharedPreferencesUtils.USER_NAME, userName)
        intent.putExtra(SharedPreferencesUtils.USER_PASSWORD, userPassword)
        startActivity(intent)
    }

}