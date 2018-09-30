package com.example.lengary_l.wanandroidtodo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.lengary_l.wanandroidtodo.ui.home.HomeFragment
import com.example.lengary_l.wanandroidtodo.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mHomeFragment: HomeFragment
    private lateinit var mSettingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragments(savedInstanceState)
        showFragment(mHomeFragment)
        buttomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> showFragment(mHomeFragment)
                else -> showFragment(mSettingsFragment)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        title = getString(R.string.nav_home)
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        val fm = supportFragmentManager
        if (savedInstanceState == null){
            mHomeFragment = HomeFragment.newInstance()
            mSettingsFragment = SettingsFragment.newInstance()
        }else {
            mHomeFragment = fm.getFragment(savedInstanceState, HomeFragment::class.java.simpleName) as HomeFragment
            mSettingsFragment = fm.getFragment(savedInstanceState, SettingsFragment::class.java.simpleName) as SettingsFragment
        }

        if (!mHomeFragment.isAdded) {
            fm.beginTransaction()
                    .add(R.id.container, mHomeFragment, HomeFragment::class.java.simpleName)
                    .commit()
        }

        if (!mSettingsFragment.isAdded) {
            fm.beginTransaction()
                    .add(R.id.container, mSettingsFragment, SettingsFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun showFragment(fragment: Fragment): Boolean {
        val fm = supportFragmentManager
        when(fragment){
            is HomeFragment -> fm.beginTransaction()
                    .show(mHomeFragment)
                    .hide(mSettingsFragment)
                    .commit()

            is SettingsFragment -> fm.beginTransaction()
                    .show(mSettingsFragment)
                    .hide(mHomeFragment)
                    .commit()
        }
        return true

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val fm = supportFragmentManager
        outState?.let {
            fm.putFragment(it, HomeFragment::class.java.simpleName, mHomeFragment)
            fm.putFragment(it, SettingsFragment::class.java.simpleName, mSettingsFragment) }
    }

}
