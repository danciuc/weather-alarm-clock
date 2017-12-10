package com.example.android.weatheralarmclock.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.android.weatheralarmclock.AppDatabase


class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private var db: AppDatabase? = null

    var weatherList: LiveData<List<Weather>>? = null
        private set

    init {
        db = AppDatabase.getInstance(this.getApplication())
        weatherList = db!!.weatherDao().loadWeatherByLocation("Cluj-Napoca")
    }

//    private fun subscribeToDbChanges() {
//        weatherList = db.weatherDao().loadWeatherByLocation("Cluj-Napoca")
//    }
}
