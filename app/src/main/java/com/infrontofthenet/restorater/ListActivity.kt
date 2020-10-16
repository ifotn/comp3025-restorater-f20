package com.infrontofthenet.restorater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    // connect to Firestore
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // set our recyclerview to use LinearLayour
        restaurantsRecyclerView.layoutManager = LinearLayoutManager(this)

        // query the db for all restaurants
        val query = db.collection("restaurants").orderBy("name", Query.Direction.ASCENDING)
    }

    // create inner classes needed to bind the data to the recyclerview
    private inner class RestaurantViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {}

    private inner class RestaurantAdapter internal constructor(options: FirestoreRecyclerOptions<Restaurant>) :
            FirestoreRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(
            holder: RestaurantViewHolder,
            position: Int,
            model: Restaurant
        ) {
            TODO("Not yet implemented")
        }

    }
}