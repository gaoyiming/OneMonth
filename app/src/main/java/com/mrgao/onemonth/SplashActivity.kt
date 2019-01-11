package com.mrgao.onemonth

import android.app.Activity
import android.os.Bundle
import com.mrgao.onemonth.utils.PreferencesUtil
import com.mrgao.onemonth.utils.RxUtil
import com.mrgao.onemonth.view.LoginActivity
import org.jetbrains.anko.startActivity

class SplashActivity : Activity() {
    lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        username = PreferencesUtil.getValue("username", "").toString()
        RxUtil.countdown(3, 1)?.compose(RxUtil.rxSchedulerHelper())?.subscribe {

            if (it <= 0) {
                if (username.isEmpty()) {
                    startActivity<LoginActivity>()
                } else {
                    startActivity<MainActivity>()
                }
            }
        };


    }
}


