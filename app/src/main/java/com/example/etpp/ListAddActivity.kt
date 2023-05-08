package com.example.etpp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListAddActivity : AppCompatActivity() {

    lateinit var addFab: FloatingActionButton
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_add)

        addFab = findViewById(R.id.add_fab)

        Log.i(toString(),"before db code in listadd")

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "foods")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        Log.i(toString(),"after db code listadd")

        addFab.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@ListAddActivity,
                    CreateFoodActivity::class.java
                )
            )
        })

        val foodList = db!!.foodDao()!!.allFoods

        val foods: ArrayList<*>? = foodList as ArrayList<*>?

        val foodsAdapter = FoodsAdapter(this, foods as ArrayList<Food?>?)

        val listView = findViewById<ListView>(R.id.main_list)

        listView.adapter = foodsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.removeItem(R.id.item_main_page)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.item_by_day) {
            val intent = Intent(this, ByDayActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.item_by_meal) {
            val intent = Intent(this, ByMealActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.item_by_search) {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.item_add_food) {
            val intent = Intent(this, CreateFoodActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.item_delete_all) {
            val intent = Intent(this, ClearAllActivity::class.java)
            startActivity(intent)
        }

        if (item.itemId == R.id.item_week_summary) {
            val intent = Intent(this, WeeklySummaryActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    fun getFood(listItem: View) {
        val foodID = listItem.tag as Int
        val intent = Intent(this, SingleFoodActivity::class.java)
        intent.putExtra("foodID", foodID)
        startActivity(intent)
    }
}