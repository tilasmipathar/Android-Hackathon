package com.example.loginpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.loginpage.daos.DishDao

class AddDishActivity : AppCompatActivity() {
    private lateinit var dishDao: DishDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dish)

        dishDao = DishDao()

        val addDishButton: Button = findViewById(R.id.addDishButton)
        val dishInput: EditText = findViewById(R.id.dishInput)
        val priceInput: EditText = findViewById(R.id.priceInput)

        addDishButton.setOnClickListener {
            val name = dishInput.text.toString().trim()
            val priceString = priceInput.text.toString().trim()

            if(priceString.isNotEmpty()) {
                val price: Double = priceString.toDouble()
                if(name.isNotEmpty() && price > 0) {
                    dishDao.addDish(name, price)
                    finish()
                }
            }
        }
    }
}