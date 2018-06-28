package com.mrgao.onemonth

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.mrgao.onemonth.base.BindingAdapter
import com.mrgao.onemonth.base.BindingHolder
import com.mrgao.onemonth.bean.Task
import com.mrgao.onemonth.model.DButil
import com.onemonth.dao.TaskDao
import kotlinx.android.synthetic.main.activity_calander.*
import java.util.*

class CalanderActivity : AppCompatActivity(), CalendarView.OnDateSelectedListener,
        CalendarView.OnYearChangeListener {


    private var mYear: Int = 0
    lateinit var bindingAdapter: BindingAdapter<Task>
    var taskList: ArrayList<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calander)


        val schemes = ArrayList<Calendar>()
        val year = calendarView.curYear
        val month = calendarView.curMonth

//        schemes.add(getSchemeCalendar(year, month, 3, -0xbf24db, "假"))
//        schemes.add(getSchemeCalendar(year, month, 6, -0x196ec8, "事"))
//        schemes.add(getSchemeCalendar(year, month, 9, -0x20ecaa, "议"))
//        schemes.add(getSchemeCalendar(year, month, 13, -0x123a93, "记"))
//        schemes.add(getSchemeCalendar(year, month, 14, -0x123a93, "记"))
//        schemes.add(getSchemeCalendar(year, month, 15, -0x5533bc, "假"))
//        schemes.add(getSchemeCalendar(year, month, 18, -0x43ec10, "记"))
//        schemes.add(getSchemeCalendar(year, month, 25, -0xec5310, "假"))
        calendarView.setSchemeDate(schemes)


        var defaultClassify = ""
        bindingAdapter = object : BindingAdapter<Task>(taskList, R.layout.item_task, BR.task) {
            override fun convert(holder: BindingHolder<*>?, position: Int, t: Task?) {
                super.convert(holder, position, t)
//                val binding = holder!!.binding as ItemTaskBinding
//                val taskDao = DButil.getDaosession().taskDao
//                val itemTaskLeftBinding = binding.smMenuViewLeft as ItemTaskLeftBinding
//                itemTaskLeftBinding.left.setOnClickListener {
//                    startActivity<SmileActivity>("id" to taskList[position].id)
//                    binding.sml.scrollX = 0
//
//                }
//                if (defaultClassify != taskList[position].classify) {
//                    binding.title.visibility = View.VISIBLE
//                }
//                defaultClassify = taskList[position].classify
//                val itemTaskReghtBinding = binding.smMenuViewRight as ItemTaskReghtBinding
//                itemTaskReghtBinding.right.setOnClickListener {
//                    binding.sml.scrollX = 0
//
//                    taskDao.delete(taskList[position])
//                    RxBus.instance.post("TODAYTASK_REFRESH")
//                }
            }

        }
        recyclerView.adapter = bindingAdapter
        bindingAdapter.dates = taskList








        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.addItemDecoration(GroupItemDecoration<String, Article>())
//        recyclerView.adapter = ArticleAdapter(this)
//        recyclerView.notifyDataSetChanged()
        tv_month_day.setOnClickListener(View.OnClickListener {
            if (!calendarLayout.isExpand) {
                calendarView.showYearSelectLayout(mYear)
                return@OnClickListener
            }
            calendarView.showYearSelectLayout(mYear)
            tv_lunar.visibility = View.GONE
            tv_year.visibility = View.GONE
            tv_month_day.text = mYear.toString()
        })
        //findViewById(R.id.fl_current).setOnClickListener(View.OnClickListener { calendarView.scrollToCurrent() })
        calendarView.setOnDateSelectedListener(this)
        calendarView.setOnYearChangeListener(this)
        tv_year.text = calendarView.curYear.toString()
        mYear = calendarView.curYear
        tv_month_day.text = calendarView.curMonth.toString() + "月" + calendarView.curDay + "日"
        tv_lunar.text = "今日"
        tv_current_day.text = calendarView.curDay.toString()
    }

    private fun getSchemeCalendar(year: Int, month: Int, day: Int, color: Int, text: String): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color//如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        return calendar
    }


    @SuppressLint("SetTextI18n")
    override fun onDateSelected(calendar: Calendar, isClick: Boolean) {

        tv_lunar.visibility = View.VISIBLE
        tv_year.visibility = View.VISIBLE
        tv_month_day.text = calendar.month.toString() + "月" + calendar.day + "日"
        tv_year.text = calendar.year.toString()
        tv_lunar.text = calendar.lunar
        mYear = calendar.year
        val smonth: String
        if (calendar.month > 9) {
            smonth = calendar.month.toString()
        } else {
            smonth = "0" + calendar.month.toString()
        }
        val sday: String
        if (calendar.day > 9) {
            sday = calendar.day.toString()
        } else {
            sday = "0" + calendar.day.toString()
        }

        val data = calendar.year.toString() + smonth + sday

        taskList = DButil.getDaosession().taskDao.queryBuilder().where(TaskDao.Properties.Data.eq(data)).list() as ArrayList<Task>

        taskList.sortBy { it.classify }
        bindingAdapter.dates = taskList
    }

    override fun onYearChange(year: Int) {
        tv_month_day.text = year.toString()
    }

}
