package com.mrgao.onemonth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrgao.onemonth.R


/**
 * A simple [Fragment] subclass.
 *
 */
class DelayTaskMvpFragment : Fragment() {
//    override fun getLayoutId(): Int {
////        return R.layout.fragment_delay_task
////        return 0
//    }
//
//    override fun initUI(view: View?, savedInstanceState: Bundle?) {
//
//
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delay_task, container, false)
    }


}
