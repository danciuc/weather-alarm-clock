package com.example.android.weatheralarmclock

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.android.weatheralarmclock.model.Alarm
import com.example.android.weatheralarmclock.model.AlarmDao

@Database(
        entities = [Alarm::class],
        version = 2,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object {

        private val DATABASE_NAME: String = "weather-alarm-clock"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
            ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
        }
    }
}
