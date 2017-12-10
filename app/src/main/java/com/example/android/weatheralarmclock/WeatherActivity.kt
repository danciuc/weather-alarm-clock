package com.example.android.weatheralarmclock

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.android.weatheralarmclock.model.Weather
import com.example.android.weatheralarmclock.model.WeatherViewModel
import com.example.android.weatheralarmclock.util.Webservice
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_weather.*
import java.io.IOException
import java.util.*


class WeatherActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        addWeatherObserver()

        button_sync_weather_now.setOnClickListener { syncWeather() }
    }

    private fun addWeatherObserver() {
        weatherViewModel.weatherList!!.observe(this, Observer { weatherList ->
            Log.w("OBSERVER", "Adding weather observer")
            if (weatherList != null) {
                if (weatherList.isEmpty()) {
                    text_view_weather.text = "No records"
                    return@Observer
                }

                val sb = StringBuilder()
                val weather = weatherList[0] // the most recent one

//                val calendar = Calendar.getInstance()
//                calendar.timeInMillis = weather.forecastTime * 1000
//                val forecastTime = calendar.time

                sb.append("\tLocation: ${weather.location}\n")
                sb.append("\tCurrent temperature: ${weather.temperature}\n")
                sb.append("\tHumidity: ${weather.humidity}%\n")
                sb.append("\tWind speed: ${weather.windSpeed} m/s\n")
                sb.append("\tDescription: ${weather.description}\n")
                sb.append("\tLast sync: ${weather.lastSync}\n\n\n")

                text_view_weather.text = sb.toString()
                displayGraph(weatherList)
            }
        })
    }

    private fun displayGraph(weatherList: List<Weather>) {
        val dataPoints = mutableListOf<DataPoint>()
        weatherList.forEachIndexed { index, weather ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = weather.forecastTime * 1000
            val dataPoint = DataPoint(calendar.get(Calendar.HOUR_OF_DAY).toDouble(), weather.temperature.toDouble())
//            val dataPoint = DataPoint(index.toDouble(), weather.temperature.toDouble())
            dataPoints.add(dataPoint)
        }

        val series = LineGraphSeries<DataPoint>(dataPoints.toTypedArray())

//        graph.removeAllSeries()
        graph.addSeries(series)

//        val series = LineGraphSeries<DataPoint>(arrayOf(DataPoint(0.toDouble(), 1.toDouble()), DataPoint(1.toDouble(), 5.toDouble()), DataPoint(2.toDouble(), 3.toDouble())))
//        graph.addSeries(series)
    }

    @Throws(IOException::class)
    private fun syncWeather() {
        Webservice(this.applicationContext).downloadWeatherForecast()
    }
}
