package com.mrgao.onemonth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mrgao.onemonth.base.BaseDatabindingFragment
import com.mrgao.onemonth.databinding.FragmentCustomBinding
import kotlinx.android.synthetic.main.fragment_custom.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 *
 */
class CustomMvpFragment : BaseDatabindingFragment<FragmentCustomBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_custom
    }

    override fun initUI(view: View?, savedInstanceState: Bundle?) {
        img_orc.setOnClickListener {
            startActivity<TakePicActivity>()
        }
    }


}
