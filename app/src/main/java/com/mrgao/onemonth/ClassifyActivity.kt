package com.mrgao.onemonth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrgao.onemonth.base.BaseBean
import com.mrgao.onemonth.base.BindingAdapter
import com.mrgao.onemonth.bean.Classify
import com.mrgao.onemonth.bean.ClassifyNet
import com.mrgao.onemonth.bean.P
import com.mrgao.onemonth.model.DButil
import com.mrgao.onemonth.net.APIUtil.createApi
import com.mrgao.onemonth.net.Api
import com.mrgao.onemonth.rx.RxBus
import com.mrgao.onemonth.utils.PreferencesUtil
import com.mrgao.onemonth.utils.RxUtil
import com.mrgao.onemonth.view.ProgressSubscriber
import com.onemonth.dao.ClassifyDao

import com.yarolegovich.lovelydialog.LovelyTextInputDialog
import kotlinx.android.synthetic.main.activity_classify.*



class ClassifyActivity : AppCompatActivity() {
    private lateinit var classifyDao: ClassifyDao
    lateinit var bindingAdapter: BindingAdapter<Classify>
    lateinit var classifyLists: ArrayList<Classify>
    lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classifyDao = DButil.daosession.classifyDao
        username = PreferencesUtil.getValue("username", "").toString()

        setContentView(R.layout.activity_classify)
        addClassify.setOnClickListener {
            LovelyTextInputDialog(this)
                    .setTopColorRes(R.color.main_color)
                    .setConfirmButton("确定", {
                        addToDatabase(it)

                    })
                    .setIcon(R.mipmap.classify)
                    .setTitle(R.string.addClassify)
                    .setNegativeButton(android.R.string.no, null)
                    .show()
        }

        classifyLists = ArrayList()
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        bindingAdapter = object : BindingAdapter<Classify>(classifyLists, R.layout.item_classify, BR.classify) {}
        recyclerView.adapter = bindingAdapter
        getData()
        RxBus.instance.register(String::class.java).subscribe({ s ->
            run {
                if ("CLASSIFY_REFRESH" == s) {
                    getData()
                }
            }
        })
    }

    private fun getData() {
        if (username.isEmpty()) {
            classifyLists = classifyDao.loadAll() as ArrayList<Classify>
            bindingAdapter.dates = classifyLists
            bindingAdapter.notifyDataSetChanged()
        } else {
            val map = HashMap<String, String>()
            map["username"] = username
            createApi(Api::class.java)
                    .getTaskType(map)
                    .compose(RxUtil.rxSchedulerHelper<BaseBean<ClassifyNet>>())
                    .subscribe(object : ProgressSubscriber<BaseBean<ClassifyNet>>(this, false) {
                        override fun onNext(t: BaseBean<ClassifyNet>) {
                        }
                    })
        }
    }

    private fun addToDatabase(classy: String?) {
        if (username.isEmpty()) {
            val classify = Classify()
            classify.classify = classy
            val insert = classifyDao.insert(classify)
            if (insert > 0) {
                RxBus.instance.post("CLASSIFY_REFRESH")
            }
        } else {
            val map = HashMap<String, String>()
            val username = PreferencesUtil.getValue("username", "")
            map["username"] = username.toString()
            map["username"] = username.toString()
            createApi(Api::class.java)
                    .addTaskType(map)
                    .compose(RxUtil.rxSchedulerHelper<BaseBean<P>>())
                    .subscribe(object : ProgressSubscriber<BaseBean<P>>(this, false) {
                        override fun onNext(next: BaseBean<P>) {
                        }
                    })


        }


    }
}
