package com.example.android.weatheralarmclock.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.android.weatheralarmclock.R
import com.example.android.weatheralarmclock.util.FirebaseDbUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    private val TAG = "CreateAccountActivity"

    // Firebase
    private var dbReference: DatabaseReference? = null
    private var db: FirebaseDatabase? = null
    private var auth: FirebaseAuth? = null

    // User account
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialize()
    }

    private fun initialize() {
        db = FirebaseDbUtil.db
        dbReference = db!!.reference.child("Users")
        auth = FirebaseAuth.getInstance()

        button_register.setOnClickListener { createAccount() }
    }

    private fun createAccount() {
        firstName = edit_text_first_name.text.toString()
        lastName = edit_text_last_name.text.toString()
        email = edit_text_email.text.toString()
        password = edit_text_password.text.toString()

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
                && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            progress_bar_create_account.visibility = View.VISIBLE

            auth!!
                    .createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        progress_bar_create_account.visibility = View.GONE

                        if (task.isSuccessful) {
                            Log.d(TAG, "createAccount:success")

                            val userId = auth!!.currentUser!!.uid
                            val currentUserDb = dbReference!!.child(userId)
                            currentUserDb.child("firstName").setValue(firstName)
                            currentUserDb.child("lastName").setValue(lastName)

                            updateUI()
                        } else {
                            Log.w(TAG, "createAccount:failed", task.exception)
                            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this, ListAlarmsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}
