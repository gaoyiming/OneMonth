package com.mrgao.onemonth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.KeyEvent
import com.moxun.tagcloudlib.view.TagCloudView
import com.mrgao.onemonth.bean.Classify
import com.mrgao.onemonth.bean.Task
import com.mrgao.onemonth.model.DButil
import com.onemonth.dao.ClassifyDao
import com.onemonth.dao.TaskDao

import kotlinx.android.synthetic.main.activity_add_task.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class AddTaskActivity : AppCompatActivity() {
    lateinit var classifyDao: ClassifyDao
    lateinit var taskDao: TaskDao
    var text_classify: String = ""
    lateinit var classifyLists: ArrayList<Classify>
    lateinit var lists: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_task)
        classifyDao = DButil.getDaosession().classifyDao
        taskDao = DButil.getDaosession().taskDao
        classifyLists = classifyDao.loadAll() as ArrayList<Classify>
        lists = ArrayList()
        for (classify in classifyLists) {
            lists.add(classify.classify)
        }

        val tagCloudView = findViewById<TagCloudView>(R.id.tagView)
        addClassify.setOnClickListener { startActivity<ClassifyActivity>() }
        val tagsAdapter = object : TextTagsAdapter(lists) {
            override fun getCheckContent(content: String) {
                classify.text = content
                text_classify = content
            }
        }

        tagCloudView.setAdapter(tagsAdapter)
        commit.setOnClickListener {
            if (TextUtils.isEmpty(text_classify)) {
                toast("请选选择一个任务分类")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(content.text.toString())) {
                toast("请输入具体任务")
                return@setOnClickListener
            }
            addToDb(text_classify, content.text.toString())
        }


    }

    private fun addToDb(text_classify: String, toString: String) {

        val task = Task()
        task.content = toString
        task.classify = text_classify
        task.createTime = System.currentTimeMillis()
        val insert = taskDao.insert(task)
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            finish()
        }
        return false
    }
}