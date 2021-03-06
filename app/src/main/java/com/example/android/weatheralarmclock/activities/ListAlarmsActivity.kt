package com.example.android.weatheralarmclock.activities

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.View
import com.example.android.weatheralarmclock.R
import com.example.android.weatheralarmclock.account.AccountGeneral
import com.example.android.weatheralarmclock.adapters.AlarmAdapter
import com.example.android.weatheralarmclock.model.AlarmViewModel
import kotlinx.android.synthetic.main.activity_list_alarms.*

class ListAlarmsActivity : BaseActivity() {

    private lateinit var alarmAdapter: AlarmAdapter
    private lateinit var alarmViewModel: AlarmViewModel

    private var userHasWriteAccess: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_alarms)

        initialize()
    }

    private fun initialize() {
        // Create a sync account
        AccountGeneral.createSyncAccount(this)

        // Set view model and observer
        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)
        addAlarmsObserver()

        // Handle the write access
        userHasWriteAccess = intent.getBooleanExtra("hasWriteAccess", false)
        if (userHasWriteAccess) {
            button_add_alarm.setOnClickListener { onEditAlarm() }
        } else {
            button_add_alarm.visibility = View.GONE
        }

        // Add listeners
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
            if (alarms != null) {
                this.alarmAdapter = AlarmAdapter(alarms, this, alarmViewModel, userHasWriteAccess)
                list_view_alarms.adapter = this.alarmAdapter
                list_view_alarms.emptyView = text_view_empty_list
            }
        })
    }
}
