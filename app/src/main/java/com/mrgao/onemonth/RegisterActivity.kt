package com.mrgao.onemonth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mrgao.onemonth.base.BaseBean
import com.mrgao.onemonth.bean.User
import com.mrgao.onemonth.net.APIUtil.createApi
import com.mrgao.onemonth.net.Api
import com.mrgao.onemonth.utils.RxUtil
import com.mrgao.onemonth.view.ProgressSubscriber
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register.onClick {
            if (et_username.text.toString().isEmpty()) {
                toast("用户名不能为空")
                return@onClick
            }
            if (et_password.text.toString().isEmpty()) {
                toast("密码不能为空")
                return@onClick
            }
            if (et_password_repeat.text.toString().isEmpty()) {
                toast("重复密码不能为空")
                return@onClick
            }
            if (et_password_repeat.text.toString() != et_password.text.toString()) {
                toast("两次输入密码不同")
                return@onClick
            }
            gotoRegister()
        }
    }

    private fun gotoRegister() {
        val map = HashMap<String, String>()
        map["username"] = et_username.text.toString()
        map["password"] = et_password.text.toString()
        map["password_repeat"] = et_password_repeat.text.toString()
        createApi(Api::class.java)
                .register(map)
                .compose(RxUtil.rxSchedulerHelper<BaseBean<User>>())
                .subscribe(object : ProgressSubscriber<BaseBean<User>>(this, false) {
                    override fun onNext(t: BaseBean<User>) {
                    }
                })

    }
}
