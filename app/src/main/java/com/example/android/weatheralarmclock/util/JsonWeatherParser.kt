package com.example.android.weatheralarmclock.util

import android.util.JsonReader
import com.example.android.weatheralarmclock.model.Weather
import java.io.IOException
import java.io.Reader
import java.io.StringReader
import java.util.*

object JsonWeatherParser {

//    val test = "{\"cod\":\"200\",\"message\":0.0039,\"cnt\":40,\"list\":[{\"dt\":1512421200,\"main\":{\"temp\":-1.33,\"temp_min\":-3.94,\"temp_max\":-1.33,\"pressure\":962.36,\"sea_level\":1036.06,\"grnd_level\":962.36,\"humidity\":94,\"temp_kf\":2.61},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13n\"}],\"clouds\":{\"all\":44},\"wind\":{\"speed\":3.13,\"deg\":260.5},\"snow\":{\"3h\":0.4375},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-04 21:00:00\"},{\"dt\":1512432000,\"main\":{\"temp\":-2.55,\"temp_min\":-4.51,\"temp_max\":-2.55,\"pressure\":962.58,\"sea_level\":1036.25,\"grnd_level\":962.58,\"humidity\":95,\"temp_kf\":1.96},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":2.88,\"deg\":276.502},\"snow\":{\"3h\":0.2525},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-05 00:00:00\"},{\"dt\":1512442800,\"main\":{\"temp\":-1.88,\"temp_min\":-3.19,\"temp_max\":-1.88,\"pressure\":963.47,\"sea_level\":1037.25,\"grnd_level\":963.47,\"humidity\":96,\"temp_kf\":1.3},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13n\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":2.81,\"deg\":292.501},\"snow\":{\"3h\":0.345},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-05 03:00:00\"},{\"dt\":1512453600,\"main\":{\"temp\":-2.2,\"temp_min\":-2.86,\"temp_max\":-2.2,\"pressure\":964.98,\"sea_level\":1039.05,\"grnd_level\":964.98,\"humidity\":97,\"temp_kf\":0.65},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13d\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":3.77,\"deg\":303.504},\"snow\":{\"3h\":0.08},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-05 06:00:00\"},{\"dt\":1512464400,\"main\":{\"temp\":-1.68,\"temp_min\":-1.68,\"temp_max\":-1.68,\"pressure\":966.49,\"sea_level\":1040.26,\"grnd_level\":966.49,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13d\"}],\"clouds\":{\"all\":56},\"wind\":{\"speed\":4.64,\"deg\":307.503},\"snow\":{\"3h\":0.145},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-05 09:00:00\"},{\"dt\":1512475200,\"main\":{\"temp\":-1.41,\"temp_min\":-1.41,\"temp_max\":-1.41,\"pressure\":966.9,\"sea_level\":1040.37,\"grnd_level\":966.9,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13d\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":4.82,\"deg\":303.504},\"snow\":{\"3h\":0.055},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-05 12:00:00\"},{\"dt\":1512486000,\"main\":{\"temp\":-3.54,\"temp_min\":-3.54,\"temp_max\":-3.54,\"pressure\":967.64,\"sea_level\":1041.44,\"grnd_level\":967.64,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":3.37,\"deg\":293.5},\"snow\":{\"3h\":0.015},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-05 15:00:00\"},{\"dt\":1512496800,\"main\":{\"temp\":-6.49,\"temp_min\":-6.49,\"temp_max\":-6.49,\"pressure\":967.65,\"sea_level\":1041.84,\"grnd_level\":967.65,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":2.58,\"deg\":255.001},\"snow\":{\"3h\":0.0075000000000001},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-05 18:00:00\"},{\"dt\":1512507600,\"main\":{\"temp\":-5.39,\"temp_min\":-5.39,\"temp_max\":-5.39,\"pressure\":967.22,\"sea_level\":1041.26,\"grnd_level\":967.22,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13n\"}],\"clouds\":{\"all\":56},\"wind\":{\"speed\":2.2,\"deg\":244.007},\"snow\":{\"3h\":0.08},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-05 21:00:00\"},{\"dt\":1512518400,\"main\":{\"temp\":-2.79,\"temp_min\":-2.79,\"temp_max\":-2.79,\"pressure\":966.5,\"sea_level\":1040.35,\"grnd_level\":966.5,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13n\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":3.15,\"deg\":261.503},\"snow\":{\"3h\":0.35},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-06 00:00:00\"},{\"dt\":1512529200,\"main\":{\"temp\":-0.86,\"temp_min\":-0.86,\"temp_max\":-0.86,\"pressure\":966.12,\"sea_level\":1039.73,\"grnd_level\":966.12,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":3.72,\"deg\":275.007},\"snow\":{\"3h\":0.54},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-06 03:00:00\"},{\"dt\":1512540000,\"main\":{\"temp\":-1.58,\"temp_min\":-1.58,\"temp_max\":-1.58,\"pressure\":966.12,\"sea_level\":1039.75,\"grnd_level\":966.12,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"light snow\",\"icon\":\"13d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":3.39,\"deg\":267.503},\"snow\":{\"3h\":0.12},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-06 06:00:00\"},{\"dt\":1512550800,\"main\":{\"temp\":0.11,\"temp_min\":0.11,\"temp_max\":0.11,\"pressure\":966.33,\"sea_level\":1039.77,\"grnd_level\":966.33,\"humidity\":95,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":44},\"wind\":{\"speed\":3.71,\"deg\":270.5},\"snow\":{\"3h\":0.02},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-06 09:00:00\"},{\"dt\":1512561600,\"main\":{\"temp\":1.11,\"temp_min\":1.11,\"temp_max\":1.11,\"pressure\":966.01,\"sea_level\":1039.03,\"grnd_level\":966.01,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":3.7,\"deg\":281.5},\"snow\":{\"3h\":0.025},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-06 12:00:00\"},{\"dt\":1512572400,\"main\":{\"temp\":-0.66,\"temp_min\":-0.66,\"temp_max\":-0.66,\"pressure\":966.63,\"sea_level\":1039.83,\"grnd_level\":966.63,\"humidity\":95,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":4.18,\"deg\":271.001},\"rain\":{\"3h\":0.01},\"snow\":{\"3h\":0.1},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-06 15:00:00\"},{\"dt\":1512583200,\"main\":{\"temp\":-2.54,\"temp_min\":-2.54,\"temp_max\":-2.54,\"pressure\":967.28,\"sea_level\":1040.61,\"grnd_level\":967.28,\"humidity\":95,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":3.01,\"deg\":259.006},\"rain\":{},\"snow\":{\"3h\":0.015},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-06 18:00:00\"},{\"dt\":1512594000,\"main\":{\"temp\":-4.15,\"temp_min\":-4.15,\"temp_max\":-4.15,\"pressure\":967.47,\"sea_level\":1041.06,\"grnd_level\":967.47,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":1.73,\"deg\":243.5},\"rain\":{},\"snow\":{\"3h\":0.025},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-06 21:00:00\"},{\"dt\":1512604800,\"main\":{\"temp\":-5.77,\"temp_min\":-5.77,\"temp_max\":-5.77,\"pressure\":967.73,\"sea_level\":1041.5,\"grnd_level\":967.73,\"humidity\":97,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":1.22,\"deg\":214.501},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-07 00:00:00\"},{\"dt\":1512615600,\"main\":{\"temp\":-7.53,\"temp_min\":-7.53,\"temp_max\":-7.53,\"pressure\":967.7,\"sea_level\":1041.63,\"grnd_level\":967.7,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":1.16,\"deg\":184.501},\"rain\":{},\"snow\":{\"3h\":0.0099999999999998},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-07 03:00:00\"},{\"dt\":1512626400,\"main\":{\"temp\":-7.98,\"temp_min\":-7.98,\"temp_max\":-7.98,\"pressure\":968.01,\"sea_level\":1042.01,\"grnd_level\":968.01,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":1.08,\"deg\":182},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-07 06:00:00\"},{\"dt\":1512637200,\"main\":{\"temp\":-2.71,\"temp_min\":-2.71,\"temp_max\":-2.71,\"pressure\":968.25,\"sea_level\":1041.86,\"grnd_level\":968.25,\"humidity\":96,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":1.51,\"deg\":204.505},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-07 09:00:00\"},{\"dt\":1512648000,\"main\":{\"temp\":-0.28,\"temp_min\":-0.28,\"temp_max\":-0.28,\"pressure\":967.54,\"sea_level\":1040.56,\"grnd_level\":967.54,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":1.47,\"deg\":145.5},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-07 12:00:00\"},{\"dt\":1512658800,\"main\":{\"temp\":-4.17,\"temp_min\":-4.17,\"temp_max\":-4.17,\"pressure\":966.83,\"sea_level\":1039.98,\"grnd_level\":966.83,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":1.11,\"deg\":121.012},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-07 15:00:00\"},{\"dt\":1512669600,\"main\":{\"temp\":-7.49,\"temp_min\":-7.49,\"temp_max\":-7.49,\"pressure\":965.9,\"sea_level\":1039.29,\"grnd_level\":965.9,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":1.12,\"deg\":142.503},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-07 18:00:00\"},{\"dt\":1512680400,\"main\":{\"temp\":-7.43,\"temp_min\":-7.43,\"temp_max\":-7.43,\"pressure\":965.17,\"sea_level\":1038.5,\"grnd_level\":965.17,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":0.87,\"deg\":137.501},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-07 21:00:00\"},{\"dt\":1512691200,\"main\":{\"temp\":-8.55,\"temp_min\":-8.55,\"temp_max\":-8.55,\"pressure\":963.71,\"sea_level\":1036.9,\"grnd_level\":963.71,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":1.06,\"deg\":163.006},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-08 00:00:00\"},{\"dt\":1512702000,\"main\":{\"temp\":-8.1,\"temp_min\":-8.1,\"temp_max\":-8.1,\"pressure\":961.76,\"sea_level\":1034.63,\"grnd_level\":961.76,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":1.51,\"deg\":185.502},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-08 03:00:00\"},{\"dt\":1512712800,\"main\":{\"temp\":-6.49,\"temp_min\":-6.49,\"temp_max\":-6.49,\"pressure\":960.11,\"sea_level\":1032.81,\"grnd_level\":960.11,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":44},\"wind\":{\"speed\":1.45,\"deg\":192.501},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-08 06:00:00\"},{\"dt\":1512723600,\"main\":{\"temp\":-1.37,\"temp_min\":-1.37,\"temp_max\":-1.37,\"pressure\":958.8,\"sea_level\":1030.88,\"grnd_level\":958.8,\"humidity\":97,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":1.6,\"deg\":194.507},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-08 09:00:00\"},{\"dt\":1512734400,\"main\":{\"temp\":3.09,\"temp_min\":3.09,\"temp_max\":3.09,\"pressure\":956.84,\"sea_level\":1028.26,\"grnd_level\":956.84,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.69,\"deg\":189},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-08 12:00:00\"},{\"dt\":1512745200,\"main\":{\"temp\":-0.17,\"temp_min\":-0.17,\"temp_max\":-0.17,\"pressure\":955.87,\"sea_level\":1027.56,\"grnd_level\":955.87,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":1.86,\"deg\":181.001},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-08 15:00:00\"},{\"dt\":1512756000,\"main\":{\"temp\":-2.86,\"temp_min\":-2.86,\"temp_max\":-2.86,\"pressure\":955.12,\"sea_level\":1027.05,\"grnd_level\":955.12,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":1.73,\"deg\":186},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-08 18:00:00\"},{\"dt\":1512766800,\"main\":{\"temp\":-2.49,\"temp_min\":-2.49,\"temp_max\":-2.49,\"pressure\":954.51,\"sea_level\":1026.26,\"grnd_level\":954.51,\"humidity\":78,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":2.23,\"deg\":185.503},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-08 21:00:00\"},{\"dt\":1512777600,\"main\":{\"temp\":-1.85,\"temp_min\":-1.85,\"temp_max\":-1.85,\"pressure\":953.31,\"sea_level\":1025.06,\"grnd_level\":953.31,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":2.48,\"deg\":178.002},\"rain\":{},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-09 00:00:00\"},{\"dt\":1512788400,\"main\":{\"temp\":-3.9,\"temp_min\":-3.9,\"temp_max\":-3.9,\"pressure\":951.48,\"sea_level\":1023.13,\"grnd_level\":951.48,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":1.71,\"deg\":142.504},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-09 03:00:00\"},{\"dt\":1512799200,\"main\":{\"temp\":-4.52,\"temp_min\":-4.52,\"temp_max\":-4.52,\"pressure\":950.16,\"sea_level\":1021.76,\"grnd_level\":950.16,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":2.26,\"deg\":164.001},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-09 06:00:00\"},{\"dt\":1512810000,\"main\":{\"temp\":1.6,\"temp_min\":1.6,\"temp_max\":1.6,\"pressure\":949.27,\"sea_level\":1020.24,\"grnd_level\":949.27,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.97,\"deg\":167.002},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-09 09:00:00\"},{\"dt\":1512820800,\"main\":{\"temp\":4.7,\"temp_min\":4.7,\"temp_max\":4.7,\"pressure\":948.26,\"sea_level\":1018.93,\"grnd_level\":948.26,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":3.36,\"deg\":183},\"rain\":{\"3h\":0.02},\"snow\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-12-09 12:00:00\"},{\"dt\":1512831600,\"main\":{\"temp\":2.77,\"temp_min\":2.77,\"temp_max\":2.77,\"pressure\":949.62,\"sea_level\":1020.69,\"grnd_level\":949.62,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":3.51,\"deg\":202.001},\"rain\":{\"3h\":0.03},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-09 15:00:00\"},{\"dt\":1512842400,\"main\":{\"temp\":1.17,\"temp_min\":1.17,\"temp_max\":1.17,\"pressure\":951.56,\"sea_level\":1023.07,\"grnd_level\":951.56,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":1.91,\"deg\":217.501},\"rain\":{\"3h\":0.03},\"snow\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-12-09 18:00:00\"}],\"city\":{\"id\":681290,\"name\":\"Cluj-Napoca\",\"coord\":{\"lat\":46.7667,\"lon\":23.6},\"country\":\"RO\"}}"

    private val FORECAST_LIMIT = 10

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
