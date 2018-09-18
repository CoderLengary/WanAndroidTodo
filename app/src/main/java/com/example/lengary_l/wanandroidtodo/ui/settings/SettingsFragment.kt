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
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.preference.PreferenceFragmentCompat
import android.widget.Toast
import com.example.lengary_l.wanandroidtodo.BuildConfig
import com.example.lengary_l.wanandroidtodo.R

/**
 * Created by CoderLengary
 */
class SettingsFragment: PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings_pref)
        findPreference("version").summary = BuildConfig.VERSION_NAME
        findPreference("rate").setOnPreferenceClickListener {
            try {
                val uri = Uri.parse("market://details?id=${activity?.packageName}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }catch (ex: android.content.ActivityNotFoundException) {
                showMessage(R.string.error)
            }
            true
        }

        findPreference("licenses").setOnPreferenceClickListener {
            true
        }

        findPreference("lizhaotailang").setOnPreferenceClickListener {
            openInBrowser(getString(R.string.settings_lizhaotailang_link))
            true
        }

        findPreference("hongyang").setOnPreferenceClickListener {
            openInBrowser(getString(R.string.settings_hongyang_link))
            true
        }

        findPreference("source_code").setOnPreferenceClickListener {
            openInBrowser(getString(R.string.settings_source_code_link))
            true
        }

        findPreference("send_advice").setOnPreferenceClickListener {
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

            true
        }
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

}