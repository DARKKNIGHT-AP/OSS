package com.example.etpp


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room


class ByMealActivity : AppCompatActivity() {

    lateinit var showFoodByMeal: Button
    lateinit var mealSpinner: Spinner
    lateinit var listByMeal: ListView
    lateinit var db: AppDatabase
    lateinit var noFoodText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_by_meal)

        showFoodByMeal = findViewById(R.id.show_by_meal_button)
        mealSpinner = findViewById(R.id.meal_spinner_by_meal)
        listByMeal = findViewById(R.id.list_by_meal)
        noFoodText = findViewById(R.id.no_food_for_meal)

        val that: Context = this


        Log.i(toString(),"before db code seebymeal")

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "foods")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        Log.i(toString(),"after db code seebymeal")

        val mealAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Meals.values())
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mealSpinner.adapter = mealAdapter

        showFoodByMeal.setOnClickListener(View.OnClickListener {

            val foodList = db!!.foodDao().findByMeal(mealSpinner.selectedItem.toString())
            val foods: ArrayList<*>? = foodList as ArrayList<*>?

            if (foods!!.size == 0) {

                listByMeal.visibility = View.INVISIBLE
                noFoodText.visibility = View.VISIBLE
                val noFoodTextString = "No food recorded"
                noFoodText.text = noFoodTextString
            }
            else
            {
                noFoodText.visibility = View.INVISIBLE
                listByMeal.visibility = View.VISIBLE
                val foodsMealAdapter = FoodsMealAdapter(that, foods as ArrayList<Food?>?)
                listByMeal.adapter = foodsMealAdapter
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.removeItem(R.id.item_by_meal)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_main_page) {
            val intent = Intent(this, ListAddActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.item_by_day) {
            val intent = Intent(this, ByDayActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.item_by_search) {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.item_delete_all) {
            val intent = Intent(this, ClearAllActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.item_add_food) {
            val intent = Intent(this, CreateFoodActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.item_week_summary) {
            val intent = Intent(this, WeeklySummaryActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
