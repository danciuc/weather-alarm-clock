package com.example.android.weatheralarmclock.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.android.weatheralarmclock.util.AppDatabase

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    private var db: AppDatabase = AppDatabase.getInstance(this.getApplication())

    var alarms: LiveData<List<Alarm>>? = null
        private set

    init {
        subscribeToDbChanges()
    }

    fun isAlarmAlreadySet(alarm: Alarm, positionToExclude: Int?): Boolean {
        return alarms!!.value!!.filterIndexed { index, filterAlarm ->
            positionToExclude != index
                    && alarm.hour == filterAlarm.hour
                    && alarm.minute == filterAlarm.minute
        }.isNotEmpty()
    }

    fun insertAlarm(alarm: Alarm) {
        db.alarmDao().insertAlarm(alarm)
    }

    fun deleteAlarm(alarm: Alarm) {
        db.alarmDao().deleteAlarm(alarm)
    }

    fun updateAlarm(alarm: Alarm) {
        db.alarmDao().updateAlarm(alarm)
    }

    private fun subscribeToDbChanges() {
        alarms = db.alarmDao().loadAlarms()
    }
}
