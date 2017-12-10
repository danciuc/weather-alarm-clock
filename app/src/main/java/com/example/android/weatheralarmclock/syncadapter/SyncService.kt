package com.example.android.weatheralarmclock.syncadapter

import android.app.Service
import android.content.Intent
import android.os.IBinder


class SyncService : Service() {

    override fun onCreate() {
        // SyncAdapter is not Thread-safe
        synchronized(LOCK) {
            // Instantiate our SyncAdapter
            syncAdapter = SyncAdapter(this, false)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        // Return our SyncAdapter's IBinder
        return syncAdapter!!.syncAdapterBinder
    }

    companion object {
        /**
         * Lock use to synchronize instantiation of SyncAdapter.
         */
        private val LOCK = Any()
        private var syncAdapter: SyncAdapter? = null
    }
}
