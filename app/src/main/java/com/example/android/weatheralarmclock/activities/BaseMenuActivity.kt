package com.example.android.weatheralarmclock.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.android.weatheralarmclock.R
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK



open class BaseMenuActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}