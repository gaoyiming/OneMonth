package com.mrgao.onemonth

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.azhon.suspensionfab.FabAttributes
import com.azhon.suspensionfab.OnFabClickListener
import com.azhon.suspensionfab.SuspensionFab
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
//    override fun onFabClick(fab: FloatingActionButton?, tag: Any?) {
//        when (tag) {
//            "ADD_ClASSIFY" -> startActivity<ClassifyActivity>()
//            "ADD_TASK" -> startActivity<AddTaskActivity>()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            startActivity<AddTaskActivity>()
        }
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        showFragment("TODAY_TASK")
//        showFragment("FINISHED_TASK")
//        showFragment("DELAY_TASK")
        nav_view.setNavigationItemSelectedListener(this)
//        val fabTop = findViewById<SuspensionFab>(R.id.fab_top)
////构建展开按钮属性
//        val classify = FabAttributes.Builder()
//                .setBackgroundTint(Color.parseColor("#2096F3"))
//                .setSrc(resources.getDrawable(R.mipmap.classify))
//                .setFabSize(FloatingActionButton.SIZE_MINI)
//                .setPressedTranslationZ(10)
//                .setTag("ADD_ClASSIFY")
//                .build()
//        val task = FabAttributes.Builder()
//                .setBackgroundTint(Color.parseColor("#FF9800"))
//                .setSrc(resources.getDrawable(R.mipmap.task))
//                .setFabSize(FloatingActionButton.SIZE_MINI)
//                .setPressedTranslationZ(10)
//                .setTag("ADD_TASK")
//                .build()
//        val news = FabAttributes.Builder()
//                .setBackgroundTint(Color.parseColor("#03A9F4"))
//                .setSrc(resources.getDrawable(R.drawable.))
//                .setFabSize(FloatingActionButton.SIZE_MINI)
//                .setPressedTranslationZ(10)
//                .setTag(3)
//                .build()
////添加菜单
//        fabTop.addFab(task, classify)
////设置菜单点击事件
//        fabTop.setFabClickListener(this)
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
            "FINISHED_TASK" -> databindingFragment = FinishedTaskFragment()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
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
