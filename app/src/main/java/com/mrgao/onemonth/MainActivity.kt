package com.mrgao.onemonth

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            startActivity<AddTaskActivity>()
        }
        calander.setOnClickListener {
            startActivity<CalanderActivity>()
        }
        showFragment("TODAY_TASK")
        nav_view.setNavigationItemSelectedListener(this)

    }

    private fun showFragment(Tag: String) {

        val fragments = supportFragmentManager.fragments
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(Tag)
        if (fragments != null && fragments.size > 0) {
            for (frag_ment in fragments) {
                if (frag_ment != null) {
                    fragmentTransaction.hide(frag_ment)
                }
            }
        }
        if (fragment == null) {
            fragment = fragmentBuilder(Tag)
            fragmentTransaction.add(R.id.container, fragment, Tag)
            fragmentTransaction.commitAllowingStateLoss()
            return
        }
        fragmentTransaction.show(fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun fragmentBuilder(fragmentTag: String): Fragment? {
        var databindingFragment: Fragment? = null
        when (fragmentTag) {
            "TODAY_TASK" -> databindingFragment = TodayTaskFragment()
            "FINISHED_TASK" -> databindingFragment = TaskGroupFragment()
            "DELAY_TASK" -> databindingFragment = DelayTaskMvpFragment()
        }
        return databindingFragment
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.today_task -> {
                showFragment("TODAY_TASK")
            }
            R.id.future_task -> {
                showFragment("FINISHED_TASK")
            }
            R.id.delay_task -> {
                showFragment("DELAY_TASK")
            }
            R.id.setting -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_about -> {

            }
            R.id.custom -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
