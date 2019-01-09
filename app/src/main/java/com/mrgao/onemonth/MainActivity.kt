package com.mrgao.onemonth

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.allenliu.versionchecklib.v2.AllenVersionChecker
import com.allenliu.versionchecklib.v2.builder.UIData
import com.google.android.material.navigation.NavigationView
import com.mrgao.onemonth.base.BaseBean
import com.mrgao.onemonth.bean.Version
import com.mrgao.onemonth.net.APIUtil
import com.mrgao.onemonth.net.Api
import com.mrgao.onemonth.utils.AppVersionUtil
import com.mrgao.onemonth.utils.RxUtil
import com.mrgao.onemonth.view.ProgressSubscriber
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkUpdate()

        fab.setOnClickListener {
            startActivity<AddTaskActivity>()
        }
        calander.setOnClickListener {
            startActivity<CalanderActivity>()
        }
        showFragment("TODAY_TASK")
        nav_view.setNavigationItemSelectedListener(this)

    }

    private fun checkUpdate() {
        val map = HashMap<String, String>()
        val versionCode = AppVersionUtil.getVersionCode(this)
        map["versionCode"] = versionCode.toString()
        APIUtil.createApi(Api::class.java).checkUpdate(map)
                .compose(RxUtil.rxSchedulerHelper<BaseBean<Version>>())
                .subscribe(object : ProgressSubscriber<BaseBean<Version>>(this, false) {
                    override fun onNext(version: BaseBean<Version>) {
                        if (version.code == 200) {
                            val versionData = version.data
                            downLoadApk(versionData)
                        } else {
                            toast(version.message)
                        }
                    }
                })
    }

    private fun downLoadApk(versionData: Version?) {


        AllenVersionChecker.getInstance()
                .downloadOnly(UIData.create().setDownloadUrl(versionData?.url)
                        .setContent(versionData?.describe)
                        .setTitle(versionData?.title)
                ).executeMission(this)

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
                showFragment("CUSTOM")
            }
            R.id.nav_share -> {

            }
            R.id.nav_about -> {

            }
            R.id.custom -> {
                showFragment("CUSTOM")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
