package com.example.android.weatheralarmclock.util

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.android.weatheralarmclock.model.Alarm
import com.example.android.weatheralarmclock.model.AlarmDao
import com.example.android.weatheralarmclock.model.Weather
import com.example.android.weatheralarmclock.model.WeatherDao

@Database(
        entities = [Alarm::class, Weather::class],
        version = 5,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    abstract fun weatherDao(): WeatherDao

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
