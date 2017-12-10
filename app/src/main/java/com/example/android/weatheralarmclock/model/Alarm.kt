package com.example.android.weatheralarmclock.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Entity(
        tableName = "alarms",
        indices = [
            Index(value = ["hour", "minute"], unique = true)
        ]
)
data class Alarm(
        var hour: Int,
        var minute: Int,
        var label: String = "",
        var active: Boolean = true,
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
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
}
