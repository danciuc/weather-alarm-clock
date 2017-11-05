package com.example.android.weatheralarmclock

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_set_alarm.*

class SetAlarmActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)

        alarm_switch.setOnCheckedChangeListener { button, isOn ->
            if (isOn) {
                Log.d("switch listener", "is ON")
                alarm_status.text = "OFF"
            } else {
                Log.d("switch listener", "is OFF")
            }
        }
    }

}
