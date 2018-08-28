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

package com.example.lengary_l.wanandroidtodo.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.lengary_l.wanandroidtodo.MainActivity
import com.example.lengary_l.wanandroidtodo.R
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

    private val factory by lazy {
        Injection.provideLoginDataViewModelFactory(context!!)
    }

    private val viewModel: LoginDataViewModel by lazy {
         ViewModelProviders.of(this, factory)
                .get(LoginDataViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_login, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(context){
            val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.down_in)
            linear_layout?.startAnimation(translateAnimation)
        }

        linkToRegister.setOnClickListener {
            (activity as LoginActivity).showFragment(FragmentType.REGISTER)
        }


        editUser.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputLayoutUser.error = null
                return
            }

        })
        editPassword.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputLayoutPassword.error = null
                return
            }

        })

        btnLogin.setOnClickListener {
            val userName: String? = editUser.editableText.toString()
            val passWord: String? = editPassword.editableText.toString()

            if (userName?.isNotValid()!!){
                inputLayoutUser.error = "UserName is not valid"
                return@setOnClickListener
            }

            if (passWord?.isNotValid()!!){
                inputLayoutPassword.error = "Password is not valid"
                return@setOnClickListener
            }

            viewModel.setInput(userName, passWord, PostType.TYPE_LOGIN)
        }
        subscribeUi()
    }

    private fun subscribeUi() {

        viewModel.loginData.observe(viewLifecycleOwner, Observer {
            if (it?.errorCode != 0){
                inputLayoutPassword.error = it?.errorMsg
            }else {
                Log.e("LoginFragment", it.data.toString())
                SharedPreferencesUtils.putUserId(id = it.data.id)
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        })

        viewModel.exceptionData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            Log.e("LoginFragment", it)
        })

    }



}