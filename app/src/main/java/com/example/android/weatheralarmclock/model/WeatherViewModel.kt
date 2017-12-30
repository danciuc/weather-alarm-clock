package com.example.android.weatheralarmclock.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.android.weatheralarmclock.AppDatabase
import com.example.android.weatheralarmclock.util.Webservice
import java.io.IOException

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private var db: AppDatabase = AppDatabase.getInstance(this.getApplication())

    var weatherList: LiveData<List<Weather>>
        private set

    init {
        weatherList = db.weatherDao().loadWeatherByLocation("Cluj-Napoca")
    }

    @Throws(IOException::class)
    fun syncWeather() {
        Webservice(db!!).downloadWeatherForecast()
    }
}
