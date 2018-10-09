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

package com.example.lengary_l.wanandroidtodo.ui.settings

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.example.lengary_l.wanandroidtodo.BuildConfig
import com.example.lengary_l.wanandroidtodo.MainActivity
import com.example.lengary_l.wanandroidtodo.R
import com.example.lengary_l.wanandroidtodo.databinding.FragmentSettingBinding
import com.example.lengary_l.wanandroidtodo.ui.login.LoginActivity
import com.example.lengary_l.wanandroidtodo.util.SharedPreferencesUtils
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * Created by CoderLengary
 */
class SettingsFragment: Fragment() {

    private lateinit var mDataBinding: FragmentSettingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        setHasOptionsMenu(true)
        return mDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setSupportActionBar(toolBar)

        with(mDataBinding) {
            userName = SharedPreferencesUtils.getUserName()
            version = BuildConfig.VERSION_NAME
            rateListener = View.OnClickListener {
                try {
                    val uri = Uri.parse("market://details?id=${activity?.packageName}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }catch (ex: android.content.ActivityNotFoundException) {
                    showMessage(R.string.error)
                }
            }
            licensesListener = View.OnClickListener {
                val intent = Intent(context, LicensesActivity::class.java)
                startActivity(intent)
            }

            lizhaotaitangListener = View.OnClickListener {
                openInBrowser(getString(R.string.settings_lizhaotailang_link))
            }
            hongyangListener = View.OnClickListener {
                openInBrowser(getString(R.string.settings_hongyang_link))
            }
            githubSourceListener = View.OnClickListener {
                openInBrowser(getString(R.string.settings_source_code_link))
            }
            adviceListener = View.OnClickListener {
                try {
                    val uri = Uri.parse(getString(R.string.settings_mail_account))
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_send_advices))
                    intent.putExtra(Intent.EXTRA_TEXT,
                            getString(R.string.settings_device_model) + Build.MODEL + "\n"
                                    + getString(R.string.settings_sdk_version) + Build.VERSION.RELEASE + "\n"
                                    + getString(R.string.settings_version) + BuildConfig.VERSION_NAME)
                    startActivity(intent)
                }catch (ex: android.content.ActivityNotFoundException) {
                    showMessage(R.string.error_no_mail_app)
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        toolBar.title = "Settings"
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_log_out) {
            //backToLogin()
        }
        return true
    }


    companion object {
        fun newInstance() = SettingsFragment()
    }

    private fun showMessage(@StringRes resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }


    private fun openInBrowser(url: String) {
        try {
            val intent= Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }catch (ex: android.content.ActivityNotFoundException) {
            showMessage(R.string.error_no_browser_found)
        }
    }

    private fun backToLogin() {
        activity?.finish()
        val intent = Intent(context, LoginActivity::class.java)
        SharedPreferencesUtils.putAutoLogin(false)
        SharedPreferencesUtils.putUserId(-1)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}