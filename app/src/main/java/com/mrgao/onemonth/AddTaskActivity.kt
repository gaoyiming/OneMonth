package com.mrgao.onemonth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.KeyEvent
import com.mrgao.onemonth.adapter.TextTagsAdapter
import com.mrgao.onemonth.bean.Classify
import com.mrgao.onemonth.bean.Task
import com.mrgao.onemonth.bean.TaskGroup
import com.mrgao.onemonth.model.DButil
import com.mrgao.onemonth.rx.RxBus
import com.mrgao.onemonth.view.flowlayout.FlowTagLayout
import com.mrgao.onemonth.view.flowlayout.TagAdapter
import com.onemonth.dao.ClassifyDao
import com.onemonth.dao.TaskDao
import com.onemonth.dao.TaskGroupDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_task.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


class AddTaskActivity : AppCompatActivity() {
    private lateinit var classifyDao: ClassifyDao
    private lateinit var taskDao: TaskDao
    private lateinit var taskGroupDao: TaskGroupDao
    private var text_classify: String = ""
    private lateinit var classifyLists: ArrayList<Classify>
    private var lists: ArrayList<String> = ArrayList()
    private var cycleLists: ArrayList<String> = ArrayList()
    private lateinit var tagsAdapter: TextTagsAdapter
    private lateinit var flowTagsAdapter: TagAdapter
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var customDate: Date? = null
    private var isCustom: Boolean = false
    private var currentCircle = "一天"
    private var customCircle = ""
    var calender = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        val mYear = calender.get(Calendar.YEAR);
        val mMonth = calender.get(Calendar.MONTH);
        val mDay = calender.get(Calendar.DAY_OF_MONTH);


        cycleLists.add("一天")
        cycleLists.add("一周")
        cycleLists.add("半月")
        cycleLists.add("一月")
        cycleLists.add("两个月")
        cycleLists.add("三个月")
        cycleLists.add("半年")
        cycleLists.add("一年")
        cycleLists.add("自定义")
        addClassify.setOnClickListener { startActivity<ClassifyActivity>() }
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
        RxBus.instance.register(String::class.java).subscribe({ s ->
            run {
                if ("CLASSIFY_REFRESH" == s) {
                    getClassify()
                }
            }
        })
        flowTagsAdapter = TagAdapter(this)
        flowlayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE)
        flowlayout.adapter = flowTagsAdapter
        flowlayout.setOnTagSelectListener({ parent, selectedList ->
            isCustom = false
            if (selectedList != null && selectedList.size > 0) {
                val sb = StringBuilder()
                for (i in selectedList) {
                    sb.append(parent.adapter.getItem(i))
                }
                val toString = sb.toString()
                if ("自定义".equals(toString)) {
                    isCustom = true
                    DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay).show()
                }
                circle.text = toString
            } else {
                circle.text = "暂未选择"
            }
            currentCircle = circle.text as String
        })
        circle.setOnClickListener {
            if (isCustom) {
                DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay).show()

            }
        }

        flowTagsAdapter.clearAndAddAll(cycleLists)
        getClassify()


    }

    private fun getClassify() {
        classifyDao = DButil.getDaosession().classifyDao
        taskDao = DButil.getDaosession().taskDao
        taskGroupDao = DButil.getDaosession().taskGroupDao
        classifyLists = classifyDao.loadAll() as ArrayList<Classify>
        lists.clear()
        for (classify in classifyLists) {
            lists.add(classify.classify)
        }
        tagsAdapter = object : TextTagsAdapter(lists) {
            override fun getCheckContent(content: String) {
                classify.text = content
                text_classify = content
            }
        }
        tagView.setAdapter(tagsAdapter)
    }

    @SuppressLint("SimpleDateFormat")
    private fun addToDb(text_classify: String, toString: String) {


        val d = Date()
        System.out.println(d)
        val sdf = SimpleDateFormat("yyyyMMdd")
        val dateNowStr = sdf.format(d)
        var circle = 0
        var insert = 0L
        when (currentCircle) {

            "一天" -> {
                circle = 0
            }
            "一周" -> {
                circle = 6

            }
            "半月" -> {
                circle = 14
            }
            "一月" -> {
                val date = Date()
                calender.time = date
                calender.add(Calendar.MONTH, 1)
                val date2 = calender.time
                circle = ((date2.time - date.time) / (1000 * 3600 * 24)).toInt()

            }
            "两个月" -> {
                val date = Date()
                calender.time = date
                calender.add(Calendar.MONTH, 2)
                val date2 = calender.time
                circle = ((date2.time - date.time) / (1000 * 3600 * 24)).toInt()

            }
            "三个月" -> {
                val date = Date()
                calender.time = date
                calender.add(Calendar.MONTH, 3)
                val date2 = calender.time
                circle = ((date2.time - date.time) / (1000 * 3600 * 24)).toInt()

            }
            "半年" -> {
                val date = Date()
                calender.time = date
                calender.add(Calendar.MONTH, 6)
                val date2 = calender.time
                circle = ((date2.time - date.time) / (1000 * 3600 * 24)).toInt()

            }
            "一年" -> {
                val date = Date()
                calender.time = date
                calender.add(Calendar.YEAR, 1)
                val date2 = calender.time
                circle = ((date2.time - date.time) / (1000 * 3600 * 24)).toInt()


            }
            "其他" -> {
                val date = Date()
                circle = ((customDate!!.time - date.time) / (1000 * 3600 * 24)).toInt()

            }

            else -> {
            }
        }
        val currentTimeMillis = System.currentTimeMillis()
        Observable.fromIterable(0..circle).flatMap {
            var date = Date()

            calender.time = date
            calender.add(Calendar.DATE, it)
            date = calender.time
            val format = SimpleDateFormat("yyyyMMdd")
            val dateFormat = format.format(date)
            val task = Task()
            task.content = toString
            task.classify = text_classify
            task.data = dateFormat
            task.createTime = currentTimeMillis
            insert = taskDao.insert(task)
            if (it == circle) {
                val taskGroup = TaskGroup()
                var date = Date()
                val format = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat = format.format(date)
                taskGroup.createTimeS = dateFormat
                calender.time = date
                calender.add(Calendar.DATE, circle)
                date = calender.time
                taskGroup.content = toString
                taskGroup.classify = text_classify
                val dateFormatEnd = format.format(date)
                taskGroup.endTimeS = dateFormatEnd
                taskGroup.createTime = currentTimeMillis
                taskGroup.dayNum = circle + 1
                taskGroupDao.insert(taskGroup)
            }
            Observable.just(dateNowStr)

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({


                    if (insert > 0) {
                        RxBus.instance.post("TODAYTASK_REFRESH")
                        finish()
                    }

                })


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            finish()
        }
        return false
    }

    /**
     * 日期选择器对话框监听
     */
    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String
        if (mMonth + 1 < 10) {
            if (mDay < 10) {
                days = StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString()
            } else {
                days = StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).append("月").append(mDay).append("日").toString()
            }

        } else {
            if (mDay < 10) {
                days = StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString()
            } else {
                days = StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日").toString()
            }

        }
        customCircle = "" + year + monthOfYear + dayOfMonth
        customDate = Date(year, monthOfYear, dayOfMonth)
        circle.text = days
    }
}