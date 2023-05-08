package com.example.etpp


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.etpp.Helper.convertDateFormatOlder


class SingleFoodActivity : AppCompatActivity() {

    lateinit var db: AppDatabase
    lateinit var dateSingleText: TextView
    lateinit var mealSingleText: TextView
    lateinit var foodSingleText: TextView
    lateinit var commentSingleText: TextView
    lateinit var deleteButton: Button
    var foodID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_food)

        dateSingleText = findViewById(R.id.date_single_view_text)
        mealSingleText = findViewById(R.id.meal_single_view_text)
        foodSingleText = findViewById(R.id.food_single_view_text)
        commentSingleText = findViewById(R.id.comment_single_view_text)
        deleteButton = findViewById(R.id.delete_button)

        Log.i(toString(),"before db code singlefood")

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "foods")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        Log.i(toString(),"after db code singlefood")

        val intent = intent
        foodID = intent.getIntExtra("foodID", 0)

        val singleFood = db!!.foodDao().findByID(foodID)

        dateSingleText.text = convertDateFormatOlder(singleFood!!.date)
        mealSingleText.text = singleFood.meal
        foodSingleText.text = singleFood.food
        commentSingleText.text = singleFood.comment
    }

    fun confirmDelete(v: View?) {
        val dialogClickListener =
            DialogInterface.OnClickListener {
                    dialog, which
                ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE ->
                    {
                        db!!.foodDao().deleteByID(foodID)
                        startActivity(Intent(this@SingleFoodActivity, ListAddActivity::class.java))
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {

                    }
                }
            }
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this entry?").setNegativeButton("No", dialogClickListener)
            .setPositiveButton("Yes", dialogClickListener).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
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
        if (item.itemId == R.id.item_by_search) {
            val intent = Intent(this, SearchActivity::class.java)
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
