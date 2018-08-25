package com.example.lengary_l.wanandroidtodo.mvvm.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.lengary_l.wanandroidtodo.R

class LoginActivity : AppCompatActivity() {
    private lateinit var mLoginFragment: LoginFragment
    private lateinit var mRegisterFragment: RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        initsFragment(savedInstanceState)
        showFragment(FragmentType.LOGIN)
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
            FragmentType.LOGIN  -> fm.beginTransaction()
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


}
