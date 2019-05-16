package com.mrgao.onemonth

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                showFragment("TODAY_TASK")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                showFragment("CUSTOM")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                showFragment("FINISHED_TASK")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        fab.setOnClickListener {
            startActivity<AddTaskActivity>()
        }
        calander.setOnClickListener {
            startActivity<CalanderActivity>()
        }
        showFragment("TODAY_TASK")
    }

    private fun showFragment(Tag: String) {

        val fragments = supportFragmentManager.fragments
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(Tag)
        if (fragments.size > 0) {
            for (frag_ment in fragments) {
                if (frag_ment != null) {
                    fragmentTransaction.hide(frag_ment)
                }
            }
        }
        if (fragment == null) {
            fragment = fragmentBuilder(Tag)
            if (fragment != null) {
                fragmentTransaction.add(R.id.container, fragment, Tag)
            }
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
            "CUSTOM" -> databindingFragment = CustomMvpFragment()
        }
        return databindingFragment
    }
}
