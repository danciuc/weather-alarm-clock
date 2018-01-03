package com.example.android.weatheralarmclock.util

import com.example.android.weatheralarmclock.model.Weather
import com.example.android.weatheralarmclock.model.WeatherDao
import okhttp3.*
import java.io.IOException


class Webservice(private val db: AppDatabase) {

    private val client: OkHttpClient = OkHttpClient()

    @Throws(IOException::class)
    fun downloadWeatherForecast() {
        val request = Request.Builder()
                .url(WEATHER_API_URL)
                .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val json = response.body()?.string() ?: return

                val weatherModels = JsonWeatherParser.buildWeatherModels(json, LOCATION)
                saveWeatherModels(weatherModels)
            }
        })
    }

    private fun saveWeatherModels(weatherModels: List<Weather>) {
        WeatherDao.refreshWeatherByLocation(db.weatherDao(), LOCATION, weatherModels)
    }

    companion object {
        private val LOCATION = "Cluj-Napoca"
        private val WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=$LOCATION,RO&units=metric&APPID=e19222d9771bdc1c16b49da4e0ec39e6"
    }
}
