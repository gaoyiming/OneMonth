package com.mrgao.onemonth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrgao.onemonth.base.BindingAdapter
import com.mrgao.onemonth.bean.Classify
import com.mrgao.onemonth.model.DButil
import com.mrgao.onemonth.rx.RxBus
import com.onemonth.dao.ClassifyDao

import com.yarolegovich.lovelydialog.LovelyTextInputDialog
import kotlinx.android.synthetic.main.activity_classify.*



class ClassifyActivity : AppCompatActivity() {
    lateinit var classifyDao: ClassifyDao
    lateinit var bindingAdapter: BindingAdapter<Classify>
    lateinit var classifyLists: ArrayList<Classify>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classifyDao = DButil.daosession.classifyDao


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
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bindingAdapter = object : BindingAdapter<Classify>(classifyLists, R.layout.item_classify, BR.classify) {


        }
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
        classifyLists = classifyDao.loadAll() as ArrayList<Classify>
        bindingAdapter.dates=classifyLists
        bindingAdapter.notifyDataSetChanged()

    }

    private fun addToDatabase(classy: String?) {


        val classify = Classify()
        classify.classify = classy
        val insert = classifyDao.insert(classify)
        if (insert > 0) {
            RxBus.instance.post("CLASSIFY_REFRESH")
        }

    }
}
