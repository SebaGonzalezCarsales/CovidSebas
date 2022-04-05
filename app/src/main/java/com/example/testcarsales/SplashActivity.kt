package com.example.testcarsales

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import au.com.carsales.basemodule.context
import com.example.routertest.getNewModuleService

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //3000 = 3sec
        val countDownTimer = object: CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                //when finish countdown, start mainActivity and finish() for prevent backpressed
                startActivity(Intent(this@SplashActivity, context?.getNewModuleService()!!.getActivity()::class.java))
                finish()
            }
        }
        countDownTimer.start()
    }
}