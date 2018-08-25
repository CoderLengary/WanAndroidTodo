package com.example.lengary_l.wanandroidtodo.mvvm.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.util.isNotValid
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by CoderLengary
 */
class LoginFragment : Fragment() {


    companion object {
        fun newInstance() = LoginFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_login, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(context){
            val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.down_in)
            linear_layout.startAnimation(translateAnimation)
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


           Toast.makeText(context, "succeed", Toast.LENGTH_LONG).show()
        }

    }




}