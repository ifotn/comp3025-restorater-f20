package com.infrontofthenet.restorater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.item_restaurant.view.*

class ListActivity : AppCompatActivity() {

    // connect to Firestore
    val db = FirebaseFirestore.getInstance()
    private var adapter: RestaurantAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // set our recyclerview to use LinearLayour
        restaurantsRecyclerView.layoutManager = LinearLayoutManager(this)

        // query the db for all restaurants
        val query = db.collection("restaurants").orderBy("name", Query.Direction.ASCENDING)

        // pass query results to RecyclerAdapter for display in RecyclerView
        val options = FirestoreRecyclerOptions.Builder<Restaurant>().setQuery(query, Restaurant::class.java).build()
        adapter = RestaurantAdapter(options)
        restaurantsRecyclerView.adapter = adapter

        addFab.setOnClickListener {
            // navigate to Main Activity to add a Restaurant
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }



    }

    // tell adapter to start watching data for changes
    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter!!.stopListening()
        }
    }

    // create inner classes needed to bind the data to the recyclerview
    private inner class RestaurantViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {}

    private inner class RestaurantAdapter internal constructor(options: FirestoreRecyclerOptions<Restaurant>) :
            FirestoreRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {

            // inflate the item_restaurant.xml layout template to populate the recyclerview
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
            return RestaurantViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: RestaurantViewHolder,
            position: Int,
            model: Restaurant
        ) {
            // populate the restaurant name & rating into the matching TextView and RatingBar for each item in the list
            holder.itemView.nameTextView.text = model.name
            holder.itemView.ratingBar.rating = model.rating!!.toFloat() // convert to float to match RatingBar.rating type

            // Restaurant selection when RecyclerView item touched
            holder.itemView.setOnClickListener {
                val intent = Intent(applicationContext, RestaurantActivity::class.java)
                intent.putExtra("restaurantId", model.id)
                intent.putExtra("name", model.name)
                startActivity(intent)
            }
        }
    }
}