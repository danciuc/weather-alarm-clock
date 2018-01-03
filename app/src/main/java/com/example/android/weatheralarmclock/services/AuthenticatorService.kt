package com.example.android.weatheralarmclock.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.android.weatheralarmclock.account.AccountAuthenticator

class AuthenticatorService : Service() {
    private var authenticator: AccountAuthenticator? = null


    override fun onCreate() {
        // Instantiate our authenticator when the service is created
        this.authenticator = AccountAuthenticator(this)
    }

    override fun onBind(intent: Intent): IBinder? {
        // Return the authenticator's IBinder
        return authenticator!!.iBinder
    }
}
