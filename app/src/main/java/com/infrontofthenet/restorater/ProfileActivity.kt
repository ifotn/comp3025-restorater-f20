package com.infrontofthenet.restorater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    // FirebaseAuth instance
    private val authDb = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // populate the textviews based on the current user's profile
        if (authDb.currentUser != null) {
            nameTextView.text = authDb.currentUser!!.displayName
            emailTextView.text = authDb.currentUser!!.email
        }
        else {
            logout()
        }

        profileLogoutFab.setOnClickListener {
            logout()
        }

        // enable scrolling on the Terms textview
        termsTextView.movementMethod = ScrollingMovementMethod()
    }

    private fun logout() {
        authDb.signOut()
        finish()
        startActivity(Intent(this, SignInActivity::class.java))
    }
}