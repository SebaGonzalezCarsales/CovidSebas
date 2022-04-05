package com.gonzalezcs.covidnewmodule.ui.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

/*
This class contains all the functions for animations, visibility or any other view state demanded
* */
class AnimationViewClass {
    /*
    * @Function: setViewAnimationVisibility
    * @Param view (View)
    * @Param visibility (int)
    * @Param alpha (Float)
    * @Param duration (Long)
    * @Return: None
    * */
    fun setViewAnimationVisibility(view: View, visibility: Int, alpha: Float, duration: Long) {
        view.animate()
            .alpha(alpha)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = visibility
                }
            })
    }
}