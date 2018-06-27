package com.mrgao.onemonth

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
import com.mrgao.onemonth.rx.RxBus
import com.onemonth.dao.TaskDao
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

    private fun getData() {
        val date = Date()
        val format = SimpleDateFormat("yyyyMMdd")
        val dateFormat = format.format(date)
        taskList = DButil.getDaosession().taskDao.queryBuilder().where(TaskDao.Properties.Data.eq(dateFormat)).list() as ArrayList<Task>


        bindingAdapter.dates = taskList
    }

    override fun initUI(view: View?, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bindingAdapter = object : BindingAdapter<Task>(taskList, R.layout.item_task, BR.task) {
            override fun convert(holder: BindingHolder<*>?, position: Int, t: Task?) {
                super.convert(holder, position, t)
                val binding = holder!!.binding as ItemTaskBinding
                val taskDao = DButil.getDaosession().taskDao
                val itemTaskLeftBinding = binding.smMenuViewLeft as ItemTaskLeftBinding
                itemTaskLeftBinding.left.setOnClickListener {
                    startActivity<SmileActivity>("id" to taskList[position].id)
                    binding.sml.scrollX = 0

                }
                val itemTaskReghtBinding = binding.smMenuViewRight as ItemTaskReghtBinding
                itemTaskReghtBinding.right.setOnClickListener {
                    binding.sml.scrollX = 0

                    taskDao.delete(taskList[position])
                    RxBus.instance.post("TODAYTASK_REFRESH")
                }
            }

        }
        recyclerView.adapter = bindingAdapter
        bindingAdapter.dates = taskList
    }


}
