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

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.lengary_l.wanandroidtodo.R

class LoginActivity : AppCompatActivity() {
    private lateinit var mLoginFragment: LoginFragment
    private lateinit var mRegisterFragment: RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        initsFragment(savedInstanceState)
        showFragment(FragmentType.LOGIN)
        Log.e("LoginActivity", "onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        val fm = supportFragmentManager
        outState?.let {
            fm.putFragment(outState, LoginFragment::class.java.simpleName, mLoginFragment)
            fm.putFragment(outState, RegisterFragment::class.java.simpleName, mRegisterFragment) }
    }


    private fun initsFragment(savedInstanceState: Bundle?){
        val fm = supportFragmentManager
        if (savedInstanceState == null){
            mLoginFragment = LoginFragment.newInstance()
            mRegisterFragment = RegisterFragment.newInstance()
        }else {
            mLoginFragment = fm.getFragment(savedInstanceState, LoginFragment::class.java.simpleName) as LoginFragment
            mRegisterFragment = fm.getFragment(savedInstanceState, RegisterFragment::class.java.simpleName) as RegisterFragment
        }

        if (!mLoginFragment.isAdded){
            fm.beginTransaction()
                    .add(R.id.view_pager, mLoginFragment, LoginFragment::class.java.simpleName)
                    .commit()
        }

        if (!mRegisterFragment.isAdded){
            fm.beginTransaction()
                    .add(R.id.view_pager, mRegisterFragment, RegisterFragment::class.java.simpleName)
                    .commit()
        }
    }

    fun showFragment(type: FragmentType){
        val fm = supportFragmentManager
        when(type){
            FragmentType.LOGIN -> fm.beginTransaction()
                    .setCustomAnimations(R.anim.right_in,R.anim.right_out)
                    .hide(mRegisterFragment)
                    .show(mLoginFragment)
                    .commit()

            FragmentType.REGISTER -> fm.beginTransaction()
                    .setCustomAnimations(R.anim.left_in,R.anim.left_out)
                    .hide(mLoginFragment)
                    .show(mRegisterFragment)
                    .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("LoginActivity", "onDestroy")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
