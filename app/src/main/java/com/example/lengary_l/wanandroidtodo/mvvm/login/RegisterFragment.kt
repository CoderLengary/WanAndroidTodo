package com.example.lengary_l.wanandroidtodo.mvvm.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.util.isNotValid
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * Created by CoderLengary
 */
class RegisterFragment : Fragment()  {


    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_register, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linkToLogin.setOnClickListener {
            (activity as LoginActivity).showFragment(FragmentType.LOGIN)
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

        editRePassword.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputLayoutRePassword.error = null
                return
            }
        })

        btnRegister.setOnClickListener {
            val userName: String? = editUser.editableText.toString()
            val passWord: String? = editPassword.editableText.toString()
            val rePassword: String? = editRePassword.editableText.toString()
            if (userName?.isNotValid()!!){
                inputLayoutUser.error = "UserName is not valid"
                return@setOnClickListener
            }

            if (passWord?.isNotValid()!!){
                inputLayoutPassword.error = "Password is not valid"
                return@setOnClickListener
            }

            if (rePassword?.isNotValid()!!){
                inputLayoutRePassword.error = "Password is not valid"
                return@setOnClickListener
            }

            if (passWord != rePassword){
                inputLayoutRePassword.error = "Not match"
                return@setOnClickListener
            }

            Toast.makeText(context, "succeed", Toast.LENGTH_LONG).show()
        }
    }


}