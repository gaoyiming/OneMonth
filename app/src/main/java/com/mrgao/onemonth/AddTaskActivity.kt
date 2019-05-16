package com.mrgao.onemonth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.mrgao.onemonth.adapter.TextTagsAdapter
import com.mrgao.onemonth.base.BaseBean
import com.mrgao.onemonth.bean.BaseBeanL
import com.mrgao.onemonth.bean.Classify
import com.mrgao.onemonth.bean.P
import com.mrgao.onemonth.net.APIUtil.createApi
import com.mrgao.onemonth.net.Api
import com.mrgao.onemonth.rx.RxBus
import com.mrgao.onemonth.utils.PreferencesUtil
import com.mrgao.onemonth.utils.RxUtil
import com.mrgao.onemonth.view.ProgressSubscriber
import com.mrgao.onemonth.view.flowlayout.FlowTagLayout
import com.mrgao.onemonth.view.flowlayout.TagAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_task.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


class AddTaskActivity : AppCompatActivity() {
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
    private var circleNum = 1
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        username = PreferencesUtil.getValue("username", "").toString()




        setDefaltCircle()
        flowTagsAdapter = TagAdapter(this)
        flowlayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE)
        flowlayout.adapter = flowTagsAdapter


        flowlayout.setOnTagSelectListener { parent, selectedList ->


            if (selectedList == null || selectedList.size == 0) {
                circle.text = "暂未选择"
                return@setOnTagSelectListener
            }
            val checkCircle = parent.adapter.getItem(selectedList[0]).toString()
            when (checkCircle) {
                "自定义" -> {
                    isCustom = true
                    showDatePicker()
                }
                else -> isCustom = false
            }
            circle.text = checkCircle
            currentCircle = checkCircle
            circleNum = when (currentCircle) {
                "一天" -> 0
                "一周" -> 6
                "半月" -> 14
                "一月" -> getCircle(1)
                "两个月" -> getCircle(2)
                "三个月" -> getCircle(3)
                "半年" -> getCircle(6)
                "一年" -> getCircle(12)
                "其他" -> {
                    ((customDate!!.time - date.time) / (1000 * 3600 * 24)).toInt()
                }
                else -> -1
            }
            circleNum += 1
        }


        flowTagsAdapter.clearAndAddAll(cycleLists)
        getClassify()

        var bus = RxBus.instance.register(String::class.java).subscribe { s ->
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
            if (TextUtils.isEmpty(targetNum.text.toString())) {
                toast("请输入目标天数")
                return@setOnClickListener
            }

            addTask()
        }

        targetNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //输入文字前触发
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //text改变过程中，一般在此加入监听事件。
            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isEmpty())
                    return
                if (circleNum == 0) {
                    toast("请先选择任务周期")
                    return
                }


                //输入后触发
                if (s.toString().toInt() > circleNum) {
                    targetNum.setText("" + circleNum)
                    toast("目标天数最多为" + circleNum + "天")
                }
            }
        })
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
        val map = HashMap<String, String>()
        map["username"] = username
        createApi(Api::class.java)
                .getTaskType(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ProgressSubscriber<BaseBeanL<Classify>>(this, false) {
                    override fun onNext(t: BaseBeanL<Classify>) {
                        classifyLists = t.data as ArrayList<Classify>
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
                })
    }

    @SuppressLint("SimpleDateFormat")
    private fun addTask() {
        circleNum--
        val map = HashMap<String, String>()
        map["username"] = username
        map["classify"] = classify.text.toString()
        map["taskName"] = content.text.toString()
        map["targetDays"] = targetNum.text.toString()
        val format = SimpleDateFormat("yyyyMMdd")
        map["startDate"] = format.format(date)
        calender.time = date
        calender.add(Calendar.DATE, circleNum)
        val endDate = calender.time
        map["endDate"] = format.format(endDate)
        circleNum++
        map["totalDays"] = circleNum.toString()
        createApi(Api::class.java)
                .addTask(map)
                .compose(RxUtil.rxSchedulerHelper<BaseBean<P>>())
                .subscribe(object : ProgressSubscriber<BaseBean<P>>(this, false) {
                    override fun onNext(next: BaseBean<P>) {
                        RxBus.instance.post("TODAYTASK_REFRESH")
                    }
                })
//        val task = Task()
//        task.content = content.text.toString()
//        task.classify = classify.text.toString()
//        task.data = dateFormat
//        task.createTime = currentTimeMillis
//        circleNum -= 1
//        Observable.fromIterable(0..circleNum).flatMap {
//
//
//            calender.time = date
//            calender.add(Calendar.DATE, it)
//            var endDate = calender.time
//            val format = SimpleDateFormat("yyyyMMdd")
//            val dateFormat = format.format(endDate)
//            val task = Task()
//            task.content = content.text.toString()
//            task.classify = classify.text.toString()
//            task.data = dateFormat
//            task.createTime = currentTimeMillis
//            insert = taskDao.insert(task)
//
//            if(it==0){
//                val judgeByDayDao = DButil.daosession.judgeByDayDao
//                val unique = judgeByDayDao.queryBuilder().where(JudgeByDayDao.Properties.Data.eq(dateFormat)).unique()
//                if (unique != null) {
//                    unique.isFinish = false
//                    judgeByDayDao.update(unique)
//                }
//                val judgeByGroupDao = DButil.daosession.judgeByGroupDao
//                val judgeByGroup = judgeByGroupDao.queryBuilder()
//                        .where(JudgeByGroupDao.Properties.Data.eq(dateFormat), JudgeByGroupDao.Properties.Classify.eq(classify)).unique()
//                if (judgeByGroup != null) {
//                    judgeByGroup.isFinish = false
//
//                    judgeByGroupDao.update(judgeByGroup)
//                }
//            }
//            if (it == circleNum) {
//                val taskGroup = TaskGroup()
//
//                val format = SimpleDateFormat("yyyy-MM-dd")
//                val sdateFormat = format.format(date)
//                taskGroup.createTimeS = sdateFormat
//                calender.time = date
//                calender.add(Calendar.DATE, circleNum)
//                endDate = calender.time
//                taskGroup.content = content.text.toString()
//                taskGroup.classify = classify.text.toString()
//                taskGroup.targetNum = targetNum.text.toString().toInt()
//                val dateFormatEnd = format.format(endDate)
//                taskGroup.endTimeS = dateFormatEnd
//                taskGroup.createTime = currentTimeMillis
//                taskGroup.dayNum = circleNum + 1
//                taskGroupDao.insert(taskGroup)
//
//            }
//            Observable.just(dateNowStr)
//
//        }.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    if (insert > 0) {
//                        RxBus.instance.post("TODAYTASK_REFRESH")
//                        finish()
//                    }
//                }


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
    private val onDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

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
        customDate = Date(year-1900, monthOfYear, dayOfMonth)
        circleNum = ((customDate!!.time - date.time) / (1000 * 3600 * 24)).toInt()+1
        circle.text = days
    }
}


