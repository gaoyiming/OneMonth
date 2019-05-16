package com.mrgao.onemonth.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mrgao.onemonth.HomeActivity
import com.mrgao.onemonth.R
import com.mrgao.onemonth.RegisterActivity
import com.mrgao.onemonth.base.BaseBean
import com.mrgao.onemonth.bean.User
import com.mrgao.onemonth.net.APIUtil
import com.mrgao.onemonth.net.Api
import com.mrgao.onemonth.utils.PreferencesUtil
import com.mrgao.onemonth.utils.RxUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.onClick {
            val map = HashMap<String, String>()
            map["username"] = et_username.text.toString()
            map["password"] = et_password.text.toString()
            APIUtil.createApi(Api::class.java)
                    .login(map)
                    .compose(RxUtil.rxSchedulerHelper<BaseBean<User>>())
                    .subscribe(object : ProgressSubscriber<BaseBean<User>>(this@LoginActivity, false) {
                        override fun onNext(bean: BaseBean<User>) {
                            val user = bean.data
                            PreferencesUtil.saveValue("username", user.username)
                            PreferencesUtil.saveValue("password", user.username)
                            startActivity<HomeActivity>()
                        }
                    })
        }

        register.onClick {
            startActivity<RegisterActivity>()
        }
    }


}
