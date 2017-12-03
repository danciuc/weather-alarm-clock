package com.example.android.weatheralarmclock.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Entity(
        tableName = "alarms",
        primaryKeys = ["hour", "minute"]
)
data class Alarm(
        var hour: Int,
        var minute: Int,
        var label: String = "",
        var active: Boolean = true
)

fun Int.toTimeUnitString(): String = if (this <= 9) "0" + this else this.toString()

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(alarm: Alarm)

    @Update
    fun updateAlarm(alarm: Alarm)

    @Delete
    fun deleteAlarm(alarm: Alarm)

    @Query("SELECT * FROM alarms")
    fun loadAlarms(): LiveData<List<Alarm>>

    @Query("SELECT * FROM alarms " +
            "WHERE hour = :hour " +
            "AND minute = :minute ")
    fun getAlarm(hour: Int, minute: Int): Alarm
}
