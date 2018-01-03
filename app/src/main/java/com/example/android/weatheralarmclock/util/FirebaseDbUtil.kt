package com.example.android.weatheralarmclock.util

import com.google.firebase.database.FirebaseDatabase

object FirebaseDbUtil {
    val db: FirebaseDatabase = FirebaseDatabase.getInstance()

    init {
        db.setPersistenceEnabled(true)
    }
}