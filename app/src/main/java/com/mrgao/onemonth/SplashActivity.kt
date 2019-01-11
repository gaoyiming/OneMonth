package com.mrgao.onemonth

import android.app.Activity
import android.os.Bundle
import android.transition.TransitionManager
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        text_one.onClick {
            val constraintSet = ConstraintSet()
            constraintSet.load(this@SplashActivity, R.layout.activity_splash_two)
            TransitionManager.beginDelayedTransition(constraint_layout)
            constraintSet.applyTo(constraint_layout)
        }


    }
}
