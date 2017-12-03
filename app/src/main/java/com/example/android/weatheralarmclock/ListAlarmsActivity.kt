package com.example.android.weatheralarmclock

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.weatheralarmclock.model.AlarmViewModel
import kotlinx.android.synthetic.main.activity_list_alarms.*


class ListAlarmsActivity : AppCompatActivity() {
    private lateinit var alarmAdapter: AlarmAdapter
    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_alarms)

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)

        alarmViewModel.alarms?.observe(this, Observer { alarms ->
            if (alarms != null) {
                this.alarmAdapter = AlarmAdapter(alarms, this, alarmViewModel)
                list_view_alarms.adapter = this.alarmAdapter
                list_view_alarms.emptyView = text_view_empty_list
            }
        })

        button_add_alarm.setOnClickListener { alarmAdapter.editAlarm() }
    }
}
