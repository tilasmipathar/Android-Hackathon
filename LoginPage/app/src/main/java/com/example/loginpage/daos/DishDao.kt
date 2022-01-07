package com.example.loginpage.daos

import android.util.Log
import com.example.loginpage.models.Dish
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DishDao {
    private val db = Firebase.firestore
    val collection = db.collection("dishes")

    fun addDish(name: String, price: Double){
        GlobalScope.launch {
            collection.document().set(Dish(name, price, false))
        }
    }

    fun updateAvailability(id: String){
        GlobalScope.launch {
            val available = collection.document(id).get().addOnSuccessListener { document ->
                if (document != null) {
                    if (document.data!!["available"] == true) {
                        collection.document(id).update("available", false)
                    }
                    else{
                        collection.document(id).update("available", true)
                    }
                } else {
                    Log.d("Not Found", "No such document")
                }
            }
        }
    }
}