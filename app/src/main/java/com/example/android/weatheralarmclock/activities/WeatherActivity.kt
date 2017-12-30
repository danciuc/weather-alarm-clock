package com.example.android.weatheralarmclock.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import com.example.android.weatheralarmclock.R
import com.example.android.weatheralarmclock.model.Weather
import com.example.android.weatheralarmclock.model.WeatherViewModel
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.activity_weather.*


class WeatherActivity : BaseMenuActivity() {

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        addWeatherObserver()

        button_sync_weather_now.setOnClickListener { weatherViewModel.syncWeather() }
    }

    private fun addWeatherObserver() {
        weatherViewModel.weatherList!!.observe(this, Observer { weatherList ->
            if (weatherList != null) {
                if (weatherList.isEmpty()) {
                    text_view_weather.text = getString(R.string.no_records)
                    return@Observer
                }

                val sb = StringBuilder()
                val weather = weatherList[0] // the most recent one

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
            val dataPoint = DataPoint(index.toDouble(), weather.temperature.toDouble())
            dataPoints.add(dataPoint)
        }

        val series = BarGraphSeries<DataPoint>(dataPoints.toTypedArray())

        // Add spacing between bars and draw values on top of them
        series.spacing = 20
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.BLUE

        // Manually set min/max values vor X axis
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(0.0)
        graph.viewport.setMaxX((weatherList.size - 1).toDouble())

        // Set custom labels for the time
        graph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    "${weatherList[value.toInt()].getWeatherTime().first}:00"
                } else super.formatLabel(value, isValueX)
            }
        }

        // Reload graph
        graph.removeAllSeries()
        graph.addSeries(series)
    }
}
