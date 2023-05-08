package com.example.etpp


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.etpp.Helper.addLeadingZero
import com.example.etpp.Helper.convertDateToString
import com.example.etpp.Helper.convertDatetoDBString
import com.example.etpp.Helper.getCurrentDate
import java.util.*
import kotlin.collections.ArrayList


class ByDayActivity : AppCompatActivity() {

   lateinit var dayDateButton: Button
   lateinit var showByDayButton: Button
   lateinit var db: AppDatabase
   lateinit var dateForDB: String
   lateinit var datePickerDialog: DatePickerDialog
   lateinit var noFoodText: TextView
   lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_by_day)

        listView = findViewById(R.id.list_by_day)
        dayDateButton = findViewById(R.id.by_day_date_button)
        showByDayButton = findViewById(R.id.by_day_button)
        noFoodText = findViewById(R.id.no_food_text)


        Log.i(toString(),"before db code seebyday")


        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "foods")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        Log.i(toString(),"after db code seebyday")

        val newCalendar = Calendar.getInstance()
        val todayDate = getCurrentDate()
        val button_date = convertDateToString(todayDate)

        dateForDB = convertDatetoDBString(todayDate)
        dayDateButton.setText(button_date)

        val that: Context = this
        datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                val display_date = "Date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year
                dayDateButton.setText(display_date)
                dateForDB =
                    Integer.toString(year) + "-" + addLeadingZero(Integer.toString(monthOfYear + 1)) + "-" + addLeadingZero(
                        Integer.toString(dayOfMonth)
                    )
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        showByDayButton.setOnClickListener(View.OnClickListener {

            val foodList = db!!.foodDao().findByDay(dateForDB)
            val foods: ArrayList<*>? = foodList as ArrayList<*>?

            if (foods!!.size == 0) {

                listView.visibility = View.INVISIBLE
                noFoodText.visibility = View.VISIBLE

                val noFoodTextString = "No food recorded"
                noFoodText.text = noFoodTextString
            }
            else
            {
                noFoodText.visibility = View.GONE
                listView.visibility = View.VISIBLE
                val foodsDayAdapter = FoodsDayAdapter(that, foods as ArrayList<Food?>?)
                listView.adapter = foodsDayAdapter
            }
        })
    }

    fun showDatePickerDialog(v: View?) {
        datePickerDialog!!.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.removeItem(R.id.item_by_day)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_main_page) {
            val intent = Intent(this, ListAddActivity::class.java)
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
