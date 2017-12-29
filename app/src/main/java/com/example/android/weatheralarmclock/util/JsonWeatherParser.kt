package com.example.android.weatheralarmclock.util

import android.util.JsonReader
import com.example.android.weatheralarmclock.model.Weather
import java.io.IOException
import java.io.Reader
import java.io.StringReader
import java.util.*

object JsonWeatherParser {
    private val FORECAST_LIMIT = 7

    @Throws(IOException::class)
    fun buildWeatherModels(weatherJsonString: String, location: String): List<Weather> {
        val reader = JsonReader(StringReader(weatherJsonString) as Reader?)
        val weatherObjects = mutableListOf<Weather>()
        var count = 0

        reader.beginObject()

        while (reader.hasNext()) {
            val name = reader.nextName()
            if (name == "list") {

                val weatherInfo = HashMap<String, String>()

                reader.beginArray()
                while (reader.hasNext() && count < FORECAST_LIMIT) {

                    reader.beginObject()
                    while (reader.hasNext()) {
                        val innerName = reader.nextName()
                        when (innerName) {
                            "dt" -> weatherInfo.put("forecastTime", reader.nextLong().toString())

                            "main" -> {
                                reader.beginObject()
                                while (reader.hasNext()) {
                                    val mainName = reader.nextName()
                                    when (mainName) {
                                        "temp" -> weatherInfo.put("temperature", reader.nextDouble().toString())
                                        "humidity" -> weatherInfo.put("humidity", reader.nextDouble().toString())
                                        else -> reader.skipValue()
                                    }
                                }
                                reader.endObject()
                            }

                            "weather" -> {
                                reader.beginArray()
                                reader.beginObject()
                                while (reader.hasNext()) {
                                    val weatherName = reader.nextName()
                                    when (weatherName) {
                                        "description" -> weatherInfo.put("description", reader.nextString())
                                        else -> reader.skipValue()
                                    }
                                }
                                reader.endObject()
                                reader.endArray()
                            }

                            "wind" -> {
                                reader.beginObject()
                                while (reader.hasNext()) {
                                    val windName = reader.nextName()
                                    when (windName) {
                                        "speed" -> weatherInfo.put("windSpeed", reader.nextDouble().toString())
                                        else -> reader.skipValue()
                                    }
                                }
                                reader.endObject()
                            }

                            else -> reader.skipValue()
                        }
                    }
                    reader.endObject()

                    weatherObjects.add(
                            Weather(
                                    location,
                                    weatherInfo["temperature"]!!.toFloat(),
                                    weatherInfo["humidity"]!!.toFloat(),
                                    weatherInfo["windSpeed"]!!.toFloat(),
                                    weatherInfo["description"]!!,
                                    weatherInfo["forecastTime"]!!.toLong()
                            )
                    )
                    count++
                }

                // Force exit
                while (reader.hasNext()) {
                    reader.beginObject()
                    while (reader.hasNext()) reader.skipValue()
                    reader.endObject()
                }

                reader.endArray()

            } else reader.skipValue()
        }
        reader.endObject()

        return weatherObjects
    }
}
