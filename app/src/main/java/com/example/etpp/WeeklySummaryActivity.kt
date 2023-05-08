package com.example.etpp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.etpp.Helper.convertDateToString
import com.example.etpp.Helper.convertDatetoDBString
import com.example.etpp.Helper.dateWeekAgo
import com.example.etpp.Helper.getCurrentDate
import java.util.*


class WeeklySummaryActivity : AppCompatActivity() {

    lateinit var weekSummaryText: TextView
    lateinit var weekSummaryTitleText: TextView
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_summary)

        weekSummaryText = findViewById(R.id.week_summary_text)
        weekSummaryTitleText = findViewById(R.id.week_summary_title_text)

        Log.i(toString(),"before db code weeksummary")

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "foods")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        Log.i(toString(),"after db code weeksummary")

        val weekAgoDate = dateWeekAgo()
        val todayDate = getCurrentDate()

        val weekAgoDateString = convertDatetoDBString(weekAgoDate)
        val todayDateString = convertDatetoDBString(todayDate)

        val normalWeekAgoDateString = convertDateToString(weekAgoDate)
        val normalTodayDateString = convertDateToString(todayDate)

        val titleText =
            "Your summary of food eaten from $normalWeekAgoDateString to $normalTodayDateString"

        weekSummaryTitleText.text = titleText

        var summary = ""

        for (meal in Meals.values())
        {
            summary += "You ate " + db!!.foodDao().getCountByMealAndDate(
                meal.toString(), weekAgoDateString,
                todayDateString
            ) + " " + meal.toString().lowercase(Locale.getDefault()) + "s \n"
        }

        weekSummaryText.text = summary
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.removeItem(R.id.item_week_summary)
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
        if (item.itemId == R.id.item_delete_all) {
            val intent = Intent(this, WeeklySummaryActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
