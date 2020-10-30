package com.infrontofthenet.restorater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant.*
import kotlinx.android.synthetic.main.item_comment.view.*

class RestaurantActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance().collection("comments")

    // instantiate adapter class to store and bind data
    private var adapter: RestaurantActivity.CommentsAdapter? = null

    // create inner classes for adapter and viewholder
    private inner class CommentsViewHolder internal constructor(private val view: View) :
    RecyclerView.ViewHolder(view) {
    }

    private inner class CommentsAdapter internal constructor(options: FirestoreRecyclerOptions<Comment>) :
    FirestoreRecyclerAdapter<Comment, RestaurantActivity.CommentsViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
            // connect the item_comment.xml template to the recyclerview
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)

            // return the ViewHolder that is now bound to item_comment.xml into the RecyclerView
            return CommentsViewHolder(view)
        }

        override fun onBindViewHolder(holder: CommentsViewHolder, position: Int, model: Comment) {
            // assign values to the viewholder elements
            // itemView refers to current item in the ViewHolder that is being bound to 1 object in the adapter
            holder.itemView.usernameTextView.text = model.username
            holder.itemView.bodyTextView.text = model.body
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        // populate Heading textview w/restaurant name
        restaurantNameTextView.text = intent.getStringExtra("name")?.toString()

        saveCommentButton.setOnClickListener {
            // create new comment and save to firebase
            if ((!TextUtils.isEmpty(usernameEditText.text)) && (!TextUtils.isEmpty(bodyEditText.text))) {
                // get new id from Firebase
                val id = db.document().id
                val comment = Comment(id, usernameEditText.text.toString(), bodyEditText.text.toString(), intent.getStringExtra("restaurantId"))
                db.document(id).set(comment)

                // clear values
                usernameEditText.setText("")
                bodyEditText.setText("")
                Toast.makeText(this, "Comment added", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Incomplete", Toast.LENGTH_LONG).show()
            }
        }

        // section for querying Firestore for existing comments for the selected restaurant
        // set RecyclerView to use LinearLayout
        commentsRecyclerView.layoutManager = LinearLayoutManager(this)

        // query Firestore
        val query = db.whereEqualTo("restaurantId", intent.getStringExtra("restaurantId"))

        // pass the query results to adapter for display, casting each result as a Comment object
        val options = FirestoreRecyclerOptions.Builder<Comment>().setQuery(query, Comment::class.java).build()
        adapter = CommentsAdapter(options)

        // bind the Comments adapter to the RecyclerView
        commentsRecyclerView.adapter = adapter
    }

    // listen for changes to the underlying data
    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    // stop listening when activity is stopped (as another activity will be visible over top)
    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }
}