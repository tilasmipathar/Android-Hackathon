package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginpage.daos.DishDao
import com.example.loginpage.models.Dish
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), DishAdapterInterface {
    private lateinit var dishDao: DishDao
    private lateinit var adapter: DishAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this, AddDishActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        dishDao = DishDao()

        val dishCollections = dishDao.collection
        val query = dishCollections.orderBy("price", Query.Direction.ASCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Dish>().setQuery(query, Dish::class.java).build()

        adapter = DishAdapter(recyclerViewOptions, this)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onButtonClicked(id: String) {
        dishDao.updateAvailability(id)
    }
}
