package com.example.android.weatheralarmclock.account

import android.accounts.Account
import android.accounts.AccountManager
import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import com.example.android.weatheralarmclock.syncadapter.SyncAdapter


object AccountGeneral {
    /**
     * This is the type of account we are using. i.e. we can specify our app or apps
     * to have different types, such as 'read-only', 'sync-only', & 'admin'.
     */
    private val ACCOUNT_TYPE = "com.android.example.datasync"

    private val ACCOUNT_NAME = "Weather Alarm Clock"

    private val SYNC_FREQUENCY = (60 * 60).toLong() // 1 hour (seconds)

    val CONTENT_AUTHORITY = "com.example.sync"

    val account: Account
        get() = Account(ACCOUNT_NAME, ACCOUNT_TYPE)

    fun createSyncAccount(context: Context) {
        // Flag to determine if this is a new account or not
        var created = false

        // Get an account and the account manager
        val account = account
        val manager = context.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager

        // Attempt to explicitly create the account with no password or extra data
        if (manager.addAccountExplicitly(account, null, null)) {
            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1)

            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true)

            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(account, CONTENT_AUTHORITY, Bundle.EMPTY, SYNC_FREQUENCY)

            created = true
        }

        // Force a sync if the account was just created
        if (created) {
            SyncAdapter.performSync()
        }
    }
}
