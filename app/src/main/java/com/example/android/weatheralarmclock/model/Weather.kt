package com.example.android.weatheralarmclock.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.android.weatheralarmclock.util.DateConverter
import java.util.*

@Entity(
        tableName = "weather",
        indices = [
            Index(value = ["location"])
        ]
)
@TypeConverters(DateConverter::class)
data class Weather(
        var location: String,
        var temperature: Float,
        var humidity: Float,
        var windSpeed: Float,
        var description: String,
        var forecastTime: Long,
        var lastSync: Date = Date(),
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
) {
    fun getWeatherTime(): Pair<Int, Int> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = forecastTime * 1000

        return Pair(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
        )
    }
}

@Dao
@TypeConverters(DateConverter::class)
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherBatch(weather: List<Weather>)

    @Query("SELECT * FROM weather " +
            "WHERE location = :location " +
            "ORDER BY forecastTime ")
    fun loadWeatherByLocation(location: String): LiveData<List<Weather>>

    @Query("DELETE FROM weather " +
            "WHERE location = :location ")
    fun deleteByLocation(location: String)

    companion object {
        @Transaction
        fun refreshWeatherByLocation(weatherDao: WeatherDao, location: String, weatherList: List<Weather>) {
            weatherDao.deleteByLocation(location)
            weatherDao.insertWeatherBatch(weatherList)
        }
    }
}
