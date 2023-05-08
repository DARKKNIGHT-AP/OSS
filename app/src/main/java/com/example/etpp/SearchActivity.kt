package com.example.etpp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room


class SearchActivity : AppCompatActivity() {

    lateinit var db: AppDatabase
    lateinit var searchText: EditText
    lateinit var searchButton: Button
    lateinit var searchListView: ListView
    lateinit var noResultsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        searchText = findViewById(R.id.search_page_text)
        searchText.requestFocus()

    }

    fun getSearchResults(v: View?) {

        searchText = findViewById(R.id.search_page_text)
        searchButton = findViewById(R.id.search_page_button)
        searchListView = findViewById(R.id.search_list)
        noResultsText = findViewById(R.id.no_food_search)

        Log.i(toString(),"before db code searchactibvty")

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "foods")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        Log.i(toString(),"after db code srchactivity")

        val searchWord = searchText.text.toString()
        val searchDBWord = "%$searchWord%"

        val foodList = db!!.foodDao().findBySearch(searchDBWord)
        val foods: ArrayList<*>? = foodList as ArrayList<*>?

        if (foods!!.size == 0) {

            noResultsText.visibility = View.VISIBLE
            searchListView.visibility = View.INVISIBLE
            val outputText = "There are no foods recorded as '$searchWord'"
            noResultsText.text = outputText
        }
        else
        {
            noResultsText.visibility = View.INVISIBLE
            searchListView.visibility = View.VISIBLE

            val foodsAdapter = FoodsAdapter(this, foods as ArrayList<Food?>?)
            searchListView.adapter = foodsAdapter

            val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.removeItem(R.id.item_by_search)


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
        if (item.itemId == R.id.item_by_meal) {
            val intent = Intent(this, ByMealActivity::class.java)
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

    fun getFood(listItem: View) {
        val foodID = listItem.tag as Int
        val intent = Intent(this, SingleFoodActivity::class.java)
        intent.putExtra("foodID", foodID)
        startActivity(intent)
    }
}
