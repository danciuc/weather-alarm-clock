package com.example.android.weatheralarmclock.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.android.weatheralarmclock.R
import com.example.android.weatheralarmclock.util.FirebaseDbUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    private var auth: FirebaseAuth? = null

    private var email: String? = null
    private var password: String? = null

    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialize()
    }

    override fun onStart() {
        super.onStart()
        auth!!.addAuthStateListener(authStateListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (authStateListener != null) {
            auth!!.removeAuthStateListener(authStateListener!!)
        }
    }

    private fun initialize() {
        auth = FirebaseAuth.getInstance()

        button_login.setOnClickListener { login() }

        button_register_account.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }

        authStateListener = FirebaseAuth.AuthStateListener {
            val user = auth!!.currentUser
            if (user != null) {
                val intent = Intent(this, ListAlarmsActivity::class.java)
                startActivity(intent)
                finish()
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.uid)
            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out")
            }
        }
    }

    private fun login() {
        email = edit_text_email.text.toString()
        password = edit_text_password.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progress_bar_login.visibility = View.VISIBLE

            auth!!
                    .signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        progress_bar_login.visibility = View.GONE

                        if (task.isSuccessful) {
                            Log.w(TAG, "login:success")
                            val currentUser = auth!!.currentUser
                            val currentUserDb = FirebaseDbUtil.db.reference
                                    .child("Users")
                                    .child(currentUser!!.uid)

                            currentUserDb.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot?) {
                                    val role = snapshot!!.child("role").value as String?
                                    val hasWriteAccess = (role != null && role == "admin")
                                    updateUI(hasWriteAccess)
                                }

                                override fun onCancelled(error: DatabaseError?) {}
                            })
                        } else {
                            Log.w(TAG, "login:failed", task.exception)
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(hasWriteAccess: Boolean) {
        val intent = Intent(this, ListAlarmsActivity::class.java)
        intent.putExtra("hasWriteAccess", hasWriteAccess)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}
