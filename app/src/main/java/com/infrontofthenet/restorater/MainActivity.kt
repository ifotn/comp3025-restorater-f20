package com.infrontofthenet.restorater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveButton.setOnClickListener {
            // validate inputs
            if ((!TextUtils.isEmpty(nameEditText.text)) && (ratingSpinner.selectedItemPosition > 0)) {
                // capture inputs into an instance of our Restaurant class
                val restaurant = Restaurant()
                restaurant.name = nameEditText.text.toString().trim()
                restaurant.rating = ratingSpinner.selectedItem.toString().toInt()

                // connect & save to Firebase. collection will be created if it doesn't exist already
                val db = FirebaseFirestore.getInstance().collection("restaurants")
                restaurant.id = db.document().id
                db.document(restaurant.id!!).set(restaurant)

                // show confirmation & clear inputs
                nameEditText.setText("")
                ratingSpinner.setSelection(0)
                Toast.makeText(this, "Restaurant Added", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Incomplete", Toast.LENGTH_LONG).show()
            }
        }

        listFab.setOnClickListener {
            finish()
        }

        // instantiate toolbar
        setSupportActionBar(topToolbar)
    }

    // 2 overrides to display menu & handle its actions
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // inflate the main menu to add the items to the toolbar
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // navigate based on which menu item was clicked
        when (item.itemId) {
            R.id.action_add -> {
                return true
            }
            R.id.action_list -> {
                startActivity(Intent(applicationContext, ListActivity::class.java))
                return true
            }
            R.id.action_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}