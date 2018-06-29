package com.mrgao.onemonth

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mrgao.onemonth.base.BaseDatabindingFragment
import com.mrgao.onemonth.base.BindingAdapter
import com.mrgao.onemonth.base.BindingHolder
import com.mrgao.onemonth.bean.Task
import com.mrgao.onemonth.databinding.FragmentTodayTaskBinding
import com.mrgao.onemonth.databinding.ItemTaskBinding
import com.mrgao.onemonth.databinding.ItemTaskLeftBinding
import com.mrgao.onemonth.databinding.ItemTaskReghtBinding
import com.mrgao.onemonth.model.DButil
import com.mrgao.onemonth.model.DButil.checkIsFinish
import com.mrgao.onemonth.rx.RxBus
import com.onemonth.dao.TaskDao
import com.onemonth.dao.TaskGroupDao
import kotlinx.android.synthetic.main.fragment_today_task.*
import org.jetbrains.anko.support.v4.startActivity
import java.text.SimpleDateFormat
import java.util.*


class TodayTaskFragment : BaseDatabindingFragment<FragmentTodayTaskBinding>() {
    lateinit var bindingAdapter: BindingAdapter<Task>
    var taskList: ArrayList<Task> = ArrayList()


    override fun getLayoutId(): Int {
        return R.layout.fragment_today_task
    }

    override fun initdate() {
        super.initdate()
        getData()
        RxBus.instance.register(String::class.java).subscribe { freshTag ->
            if ("TODAYTASK_REFRESH".endsWith(freshTag)) {
                getData()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getData() {
        val date = Date()
        val format = SimpleDateFormat("yyyyMMdd")
        val dateFormat = format.format(date)
        taskList = DButil.daosession.taskDao.queryBuilder().where(TaskDao.Properties.Data.eq(dateFormat)).list() as ArrayList<Task>

        taskList.sortBy { it.classify }
        bindingAdapter.dates = taskList
    }

    override fun initUI(view: View?, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        var defaultClassify = ""
        bindingAdapter = object : BindingAdapter<Task>(taskList, R.layout.item_task, BR.task) {
            override fun convert(holder: BindingHolder<*>?, position: Int, t: Task?) {
                super.convert(holder, position, t)
                val binding = holder!!.binding as ItemTaskBinding
                val taskDao = DButil.daosession.taskDao
                val itemTaskLeftBinding = binding.smMenuViewLeft as ItemTaskLeftBinding
                itemTaskLeftBinding.left.setOnClickListener {
                    startActivity<SmileActivity>("id" to taskList[position].id)
                    binding.sml.scrollX = 0
                }
                if (defaultClassify != taskList[position].classify) {
                    binding.title.visibility = View.VISIBLE
                }
                defaultClassify = taskList[position].classify
                val itemTaskReghtBinding = binding.smMenuViewRight as ItemTaskReghtBinding
                itemTaskReghtBinding.right.setOnClickListener {
                    binding.sml.scrollX = 0
                    deleteTask(taskDao, position)
                }
            }

        }
        recyclerView.adapter = bindingAdapter
        bindingAdapter.dates = taskList
    }

    private fun deleteTask(taskDao: TaskDao, position: Int) {
        val task = taskList[position]
        taskDao.delete(task)
        checkIsFinish(taskDao, task.data, task.classify)
        checkGroup(taskDao, task)
        RxBus.instance.post("TODAYTASK_REFRESH")
    }

    private fun checkGroup(taskDao: TaskDao, task: Task) {
        val taskGroupDao = DButil.daosession.taskGroupDao
        val list = taskDao.queryBuilder().where(TaskDao.Properties.CreateTime.eq(task.createTime)).list()
        if (list.size == 0) {
            val unique = taskGroupDao.queryBuilder().where(TaskGroupDao.Properties.CreateTime.eq(task.createTime)).unique()
            if (unique != null) {
                taskGroupDao.delete(unique)
            }
        }
    }


}
