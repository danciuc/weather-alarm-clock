package com.example.android.weatheralarmclock.syncadapter

import android.accounts.Account
import android.content.*
import android.os.Bundle
import android.util.Log
import com.example.android.weatheralarmclock.AppDatabase
import com.example.android.weatheralarmclock.account.AccountGeneral
import com.example.android.weatheralarmclock.util.Webservice
import java.io.IOException

class SyncAdapter @JvmOverloads constructor(context: Context, autoInit: Boolean, parallelSync: Boolean = false) : AbstractThreadedSyncAdapter(context, autoInit, parallelSync) {

    override fun onPerformSync(
            account: Account,
            extras: Bundle,
            authority: String,
            provider: ContentProviderClient,
            syncResult: SyncResult
    ) {
        Log.w(TAG, "Starting synchronization...")

        try {
            syncWeather()
        } catch (ex: Exception) {
            Log.e(TAG, "Error synchronizing!", ex)
        }

        Log.w(TAG, "Finished synchronization!")
    }

    @Throws(IOException::class)
    private fun syncWeather() {
        val db = AppDatabase.getInstance(context)
        Webservice(db).downloadWeatherForecast()
    }

    companion object {
        private val TAG = "SYNC_ADAPTER"

        fun performSync() {
            val bundle = Bundle()
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true)

            ContentResolver.requestSync(
                    AccountGeneral.account,
                    AccountGeneral.CONTENT_AUTHORITY,
                    bundle
            )
        }
    }
}
