package com.example.loginpage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.loginpage.models.Dish
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class DishAdapter(options: FirestoreRecyclerOptions<Dish>, private val listener: DishAdapterInterface) : FirestoreRecyclerAdapter<Dish, DishAdapter.DishViewHolder>(
    options) {

    private lateinit var context: Context

    class DishViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dishName: TextView = itemView.findViewById(R.id.dishName)
        val dishPrice: TextView = itemView.findViewById(R.id.dishPrice)
        val availabilityButton: Button = itemView.findViewById(R.id.availabilityButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val viewHolder =  DishViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dish_item, parent, false))
        viewHolder.availabilityButton.setOnClickListener{
            listener.onButtonClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        context = parent.context
        return viewHolder
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int, model: Dish) {
        holder.dishName.text = model.name
        holder.dishPrice.text = model.price.toBigDecimal().toPlainString()
        if(model.available){
            holder.availabilityButton.text = "Available"
            holder.availabilityButton.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
        }
        else {
            holder.availabilityButton.text = "Not Available"
            holder.availabilityButton.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        }
    }
}

interface DishAdapterInterface {
    fun onButtonClicked(id: String)
}