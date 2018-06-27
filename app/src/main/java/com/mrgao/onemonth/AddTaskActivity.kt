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
    private lateinit var taskGroupDao: TaskGroupDao
    private lateinit var taskDao: TaskDao
    private lateinit var classifyLists: ArrayList<Classify>
    private lateinit var tagsAdapter: TextTagsAdapter
    private lateinit var flowTagsAdapter: TagAdapter
    private var lists: ArrayList<String> = ArrayList()
    private var cycleLists: ArrayList<String> = ArrayList()
    private var calender = Calendar.getInstance()
    private var customDate: Date? = null
    private var isCustom: Boolean = false
    private var currentCircle = "一天"
    private var customCircle = ""
    private var classifyContent = ""
    private val date = Date()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)




        setDefaltCircle()
        flowTagsAdapter = TagAdapter(this)
        flowlayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE)
        flowlayout.adapter = flowTagsAdapter


        flowlayout.setOnTagSelectListener { parent, selectedList ->
            if (selectedList == null || selectedList.size == 0) {
                circle.text = "暂未选择"
                return@setOnTagSelectListener
            }
            val checkCircle = parent.adapter.getItem(0).toString()
            when (checkCircle) {
                "自定义" -> {
                    isCustom = true
                    showDatePicker()
                }
                else -> isCustom = false
            }
            circle.text = checkCircle
            currentCircle = checkCircle
        }


        flowTagsAdapter.clearAndAddAll(cycleLists)
        getClassify()

        RxBus.instance.register(String::class.java).subscribe { s ->
            run {
                if ("CLASSIFY_REFRESH" == s) {
                    getClassify()
                }
            }
        }
        addClassify.setOnClickListener { startActivity<ClassifyActivity>() }
        commit.setOnClickListener {
            if (TextUtils.isEmpty(classifyContent)) {
                toast("请选选择一个任务分类")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(content.text.toString())) {
                toast("请输入具体任务")
                return@setOnClickListener
            }
            addToDb(classifyContent, content.text.toString())
        }

        circle.setOnClickListener {
            if (isCustom) {
                showDatePicker()
            }
        }


    }

    private fun showDatePicker() {
        val mYear = calender.get(Calendar.YEAR)
        val mMonth = calender.get(Calendar.MONTH)
        val mDay = calender.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay).show()
    }


    private fun setDefaltCircle() {
        cycleLists.add("一天")
        cycleLists.add("一周")
        cycleLists.add("半月")
        cycleLists.add("一月")
        cycleLists.add("两个月")
        cycleLists.add("三个月")
        cycleLists.add("半年")
        cycleLists.add("一年")
        cycleLists.add("自定义")
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
                classifyContent = content
            }
        }
        tagView.setAdapter(tagsAdapter)
    }

    @SuppressLint("SimpleDateFormat")
    private fun addToDb(text_classify: String, toString: String) {


        val sdf = SimpleDateFormat("yyyyMMdd")
        val dateNowStr = sdf.format(date)
        var circleNum = 0
        var insert = 0L
        when (currentCircle) {
            "一天" -> circleNum = 0
            "一周" -> circleNum = 6
            "半月" -> circleNum = 14
            "一月" -> circleNum = getCircle(1)
            "两个月" -> circleNum = getCircle(2)
            "三个月" -> circleNum = getCircle(3)
            "半年" -> circleNum = getCircle(6)
            "一年" -> circleNum = getCircle(12)
            "其他" -> {
                circleNum = ((customDate!!.time - date.time) / (1000 * 3600 * 24)).toInt()
            }
        }
        val currentTimeMillis = System.currentTimeMillis()
        Observable.fromIterable(0..circleNum).flatMap {


            calender.time = date
            calender.add(Calendar.DATE, it)
            var endDate = calender.time
            val format = SimpleDateFormat("yyyyMMdd")
            val dateFormat = format.format(endDate)
            val task = Task()
            task.content = toString
            task.classify = text_classify
            task.data = dateFormat
            task.createTime = currentTimeMillis
            insert = taskDao.insert(task)
            if (it == circleNum) {
                val taskGroup = TaskGroup()

                val format = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat = format.format(date)
                taskGroup.createTimeS = dateFormat
                calender.time = date
                calender.add(Calendar.DATE, circleNum)
                endDate = calender.time
                taskGroup.content = toString
                taskGroup.classify = text_classify
                val dateFormatEnd = format.format(endDate)
                taskGroup.endTimeS = dateFormatEnd
                taskGroup.createTime = currentTimeMillis
                taskGroup.dayNum = circleNum + 1
                taskGroupDao.insert(taskGroup)
            }
            Observable.just(dateNowStr)

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (insert > 0) {
                        RxBus.instance.post("TODAYTASK_REFRESH")
                        finish()
                    }
                }


    }

    private fun getCircle(monthNum: Int): Int {


        calender.time = date
        calender.add(Calendar.MONTH, monthNum)
        val date2 = calender.time
        return ((date2.time - date.time) / (1000 * 3600 * 24)).toInt()

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

        val days: String
        if (monthOfYear + 1 < 10) {
            days = if (dayOfMonth < 10) {
                StringBuffer().append(year).append("年").append("0")
                        .append(monthOfYear + 1).append("月").append("0").append(dayOfMonth)
                        .append("日").toString()
            } else {
                StringBuffer().append(year).append("年").append("0")
                        .append(monthOfYear + 1).append("月").append(dayOfMonth).append("日")
                        .toString()
            }

        } else {
            days = if (dayOfMonth < 10) {
                StringBuffer().append(year).append("年").append(monthOfYear + 1)
                        .append("月").append("0").append(dayOfMonth).append("日").toString()
            } else {
                StringBuffer().append(year).append("年").append(monthOfYear + 1)
                        .append("月").append(dayOfMonth).append("日").toString()
            }

        }
        customCircle = "" + year + monthOfYear + dayOfMonth
        customDate = Date(year, monthOfYear, dayOfMonth)
        circle.text = days
    }
}