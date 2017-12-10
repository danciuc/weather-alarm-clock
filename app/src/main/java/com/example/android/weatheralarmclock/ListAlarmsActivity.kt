package com.example.android.weatheralarmclock

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.android.weatheralarmclock.account.AccountGeneral
import com.example.android.weatheralarmclock.model.AlarmViewModel
import kotlinx.android.synthetic.main.activity_list_alarms.*

class ListAlarmsActivity : AppCompatActivity() {

    private lateinit var alarmAdapter: AlarmAdapter
    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alarms)

        // Create a sync account
        AccountGeneral.createSyncAccount(this)

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)

        addAlarmsObserver()

        button_add_alarm.setOnClickListener { onEditAlarm() }
        button_goto_weather.setOnClickListener { onGotoWeather() }
    }

    private fun onGotoWeather() {
        val intent = Intent(applicationContext, WeatherActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(this, "")
        builder.setContentIntent(pendingIntent)

        startActivity(intent)
    }

    private fun onEditAlarm() = alarmAdapter.editAlarm()

    private fun addAlarmsObserver() {
        alarmViewModel.alarms?.observe(this, Observer { alarms ->
            Log.w("ALARMS ACTIVITY", "In observer")
            if (alarms != null) {
                this.alarmAdapter = AlarmAdapter(alarms, this, alarmViewModel)
                list_view_alarms.adapter = this.alarmAdapter
                list_view_alarms.emptyView = text_view_empty_list
            }
        })
    }
}
