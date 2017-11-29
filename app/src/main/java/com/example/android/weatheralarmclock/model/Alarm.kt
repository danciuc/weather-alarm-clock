package com.example.android.weatheralarmclock.model

data class Alarm(
        var hour: Int,
        var minute: Int,
        var label: String = "",
        var active: Boolean = true
)

fun Int.toTimeUnitString(): String = if (this <= 9) "0" + this else this.toString()

